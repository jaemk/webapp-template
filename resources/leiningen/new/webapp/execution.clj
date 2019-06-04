(ns {{name}}.execution
  (:require [manifold.executor :refer [fixed-thread-executor]]
            [aleph.http :refer [connection-pool]]
            [{{name}}.config :as config]))

(def pool (fixed-thread-executor (config/v :num-threads)))
(def single-thread (fixed-thread-executor 1))

(def cp (connection-pool
          {:connections-per-host (config/v :max-client-connections-per-host)
           :total-connections (config/v :max-client-connections)
           :connection-options {:keep-alive? (config/v :keep-alive-client-connections)
                                :idle-timeout (config/v :keep-alive-client-timeout-ms)}}))
