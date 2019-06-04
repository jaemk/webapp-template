(ns {{name}}.database-test
  (:use midje.sweet)
  (:require [{{name}}.database.core :as db]
            [{{name}}.utils :as u]
            [{{name}}.test-utils :refer [setup-db
                                      teardown-db
                                      truncate-db]]))


(defonce test-db (atom nil))
(defonce state (atom {}))

(with-state-changes
  [(before :contents (do
                       (setup-db test-db)
                       (reset! state {})))
   (after :contents (teardown-db test-db))
   (before :facts (truncate-db test-db))]
  (facts
    (fact
      "we can create users and items"
      ; db is setup
      (nil? @test-db) => false

      ; create and save a user
      (db/create-user (:conn @test-db) "bean") =>
        (fn [result]
          (swap! state #(merge % result)) ; save the :user and :auth for later
          (and (= (-> result :user :name) "bean")
               (uuid? (-> result :auth :token))))

      ; query auth item
      (db/get-auth-by-token (:conn @test-db) (-> @state :auth :token)) =>
        (fn [auth]
          (= (-> @state :auth :id)
             (:id auth)))

      ; query user
      (db/select-users (:conn @test-db)) =>
        (fn [result]
          (and (= 1 (count result))
               (= (-> @state :user :name)
                  (-> (first result) :name)))))
    (fact
      "we can truncate the db"
      (db/select-users (:conn @test-db)) =>
        (fn [result]
          (empty? result)))))
