(ns peano.t-core
  (:require [clojure.core.logic :as l])
  (:use midje.sweet
        clojure.pprint
        peano.core))

(data [animal :by :name]
      {:name "betty" :species :bovine}
      {:name "hank" :species :equine})

(fact "data statements generate relations and facts"
  (l/run* [q] (animal?? q)) => (just "hank" "betty" :in-any-order)
  (l/run* [q] (animal?? "hank")) =not=> empty?
  (l/run* [q] (animal-species?? q :bovine)) => (just "betty"))

(fact "For completeness, the did is also used in a binary relation"
  (l/run* [q] (animal-name?? q q)) => (just "hank" "betty" :in-any-order))

(fact "data accessors are created"
   (animal-data "betty") => {:name "betty" :species :bovine}
   (animal-data "NOT PRESENT") => nil
   (animal-data) => (just {:name "betty" :species :bovine}
                          {:name "hank" :species :equine}))



;; Bindings are obeyed
(let [value 33
      who "betty"]
  (data [bindinged :by :name]
        {:name who :species value :fun odd?}
        {:name "hank" :species (+ 1 value) :fun even?}))

(fact "let bindings are obeyed"
  (l/run* [q] (bindinged?? q)) => (just "hank" "betty" :in-any-order)
  (l/run* [q] (bindinged-species?? q 33)) => (just "betty")
  (l/run* [q] (bindinged-species?? "hank" q)) => (just 34)
  ((first (l/run* [q] (bindinged-fun?? "hank" q))) 34) => true
  (bindinged-data "betty") => {:name "betty" :species 33 :fun odd?})

