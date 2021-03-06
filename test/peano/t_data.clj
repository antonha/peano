(ns peano.t-data
  (:require [clojure.core.logic :as l])
  (:use midje.sweet
        peano.data))

;; The more complete tests are in t-sweet.

;; For some reason, this eval doesn't work when run inside a fact.
(eval (did-do-form 'animal :name [{:name "bess"}]))
(fact "can make the special 'here is how I refer to data' (did) query-form"
  (l/run* [q] (animal?? q)) => ["bess"])

(eval (binary-do-form 'animal :name :hooves [{:name "bess", :hooves 4}]))
(fact "can make binary query-forms"
  (l/run* [q] (animal-hooves?? "bess" q)) => [4])


(eval (data-accessor 'animal :name [{:name "bess", :hooves 4}]))
(fact "can make the data-accessor-by-name form"
  (animal-data "bess") => {:name "bess", :hooves 4})

