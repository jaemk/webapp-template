(ns {{name}}.core-test
  (:use midje.sweet)
  (:require [{{name}}.database.core :as db]
            [{{name}}.utils :as u]
            [{{name}}.test-utils :refer [setup-db
                                      teardown-db
                                      truncate-db]]))


(defonce test-db (atom nil))

(with-state-changes
  [(before :contents (setup-db test-db))
   (after :contents (teardown-db test-db))
   (before :facts (truncate-db test-db))]
  (facts
    (fact
      "we can add"
      (+ 1 2) => 3
      (+ 2 3) => 5)))
