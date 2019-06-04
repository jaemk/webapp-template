(ns {{name}}.handlers
  (:import [java.io File]
           [io.netty.buffer PooledSlicedByteBuf])
  (:require [taoensso.timbre :as t]
            [manifold.deferred :as d]
            [manifold.stream :as s]
            [manifold.time :as dtime]
            [byte-streams :as bs]
            [aleph.http :as http]
            [clojure.java.jdbc :as j]
            [clojure.java.io :as io]
            [cheshire.core :as json]
            [{{name}}.execution :as ex]
            [{{name}}.database.core :as db]
            [{{name}}.utils :as u :refer [->resp ->text ->json]]
            [{{name}}.config :as config]))


(defn index [_]
  (->text "hello"))


(defn stream-to-file [src file]
  (d/future-with
    ex/pool
    (with-open [file-stream (io/output-stream file)]
      (io/copy src file-stream))))


(defn -make-request [uri]
  (d/chain
    (http/get uri {:pool ex/cp
                   :body (json/encode {:secret 1234})})
    :body
    bs/to-string
    #(json/decode % true)
    :data
    #(json/decode % true)
    :secret))

(defn delay-bin [req]
  (let [start (:aleph/request-arrived req)
        delay-s (-> req :params :seconds u/parse-int)]
    (->
      (d/chain
        (-make-request (format "https://httpbin.org/delay/%d" delay-s))
        (fn [resp]
          (->json {:elap (-> (System/nanoTime)
                             (- start)
                             (/ 1000000.))
                   :resps resp})))
      (d/catch
        Exception
        #(t/error "no luck.." :exc-info %)))))


(defn delay-seconds [req]
  (let [delay-ms (-> req :params :seconds u/parse-int (* 1000))]
    (dtime/in delay-ms #(->json {:msg "yo"}))))
