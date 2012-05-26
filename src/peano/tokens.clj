(ns peano.tokens
  (:require [clojure.core.logic :as l]))

(def relation-suffix "??")

(defn- symbol-maker [prefix suffix]
  (symbol (str (name prefix) suffix)))

(defn query-symbol
  ([symbol-or-string]
     (symbol-maker symbol-or-string relation-suffix))
  ([prefix-symbol suffix-key]
     (query-symbol (str (name prefix-symbol) "-" (name suffix-key)))))

(defn selector-symbol [symbol-or-string]
  (symbol-maker symbol-or-string "?>"))

(defn one-selector-symbol [symbol-or-string]
  (selector-symbol (str "one-" (name symbol-or-string))))

(defn typelike-symbol [key]
  ;; Why can I not create a symbol from a key?
  key)

(defn data-symbol [symbol-or-string]
  (symbol (str (name symbol-or-string) "-data")))

(def key-to-lvar (comp symbol name))

(defn keys-to-lvars [keys]
  (map key-to-lvar keys))

(defn canonicalize-relation [symbol-or-string]
  (let [s (name symbol-or-string)]
    (if (.endsWith s relation-suffix )
      (symbol (.substring s 0 (- (count s) (count relation-suffix))))
      symbol-or-string)))
