(ns user
  (:require [{{name}}.core :as app]
            [{{name}}.database.core :as db]
            [{{name}}.config :as config]
            [{{name}}.utils :as u]
            [{{name}}.types :as types]
            [{{name}}.commands.core :as cmd])
  (:use [midje.repl]))


(defn -main []
  (app/-main))
