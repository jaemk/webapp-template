(ns user)


(defn initenv []
  (require '[{{name}}.core :as app]
           '[{{name}}.utils :as u]
           '[{{name}}.config :as config]
           '[{{name}}.types :as types]
           '[{{name}}.database.core :as db]
           '[{{name}}.commands.core :as cmd]))
