(ns day3.solution
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [utils :refer [parse-file]]))

(def input (parse-file "day3/input.txt"))

(defn find-first
  [f coll]
  (first (filter f coll)))

(def lowercase-letters "abcdefghijklmnopqrstuvwxyz")
(def uppercase-letters (str/upper-case lowercase-letters))

(defn calculate-points [letter] 
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
              (+ (calculate-points repeated-item) total))) 0 input))

(def elf-groups (partition 3 input))

(defn part-2
  []
  (reduce (fn [total current]
            (->> (first (apply set/intersection (map set current)))
                 (calculate-points)
                 (+ total))) 0 elf-groups))

(comment 
  (part-1) ;; 8493
  (part-2) ;; 2552
  )
