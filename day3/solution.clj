(ns day3.solution
  (:require [clojure.string :as str]))

(def input (as-> (slurp "day3/input.txt") $
             (str/split $ #"\n")))

(defn find-first
  [f coll]
  (first (filter f coll)))

(def lowercase-letters "abcdefghijklmnopqrstuvwxyz")
(def uppercase-letters (str/upper-case lowercase-letters))

(defn get-points [letter] 
  (let [lowercase-score (as-> (str/index-of lowercase-letters letter) $
                          (when $ (+ $ 1)))
        uppercase-score (as-> (str/index-of uppercase-letters letter) $
                          (when $ (+ $ 27)))]
    (or lowercase-score uppercase-score)))

(defn part-1
  []
  (reduce (fn [total current]
            (let [half-index
                  (-> (count current)
                      (/ 2))
                  comp-1 (subs current 0 half-index)
                  comp-2 (subs current half-index)
                  repeated-item (find-first #(str/includes? comp-2 %) (str/split comp-1 #""))]
              (+ (get-points repeated-item) total))) 0 input))


(comment 
  (part-1) ;; 8493
  )
