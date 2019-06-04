(ns {{name}}.router
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [{{name}}.utils :as u]
            [{{name}}.handlers :as h]
            [{{name}}.config :as config]))

(defn load-routes []
  (routes
    (ANY "/" [] h/index)
    (ANY "/status" [] (u/->json {:status :ok
                                 :version (config/v :app-version)}))
    (ANY "/delay/:seconds" _ h/delay-seconds)
    (ANY "/delaybin/:seconds" _ h/delay-bin)
    (route/not-found (u/->resp
                       :body "nothing to see here"
                       :status 404))))
