(ns day4.solution
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [utils :refer [parse-file]]))

(def input (parse-file "day4/input.txt"))

(def inclusive-range (fn [arr]
                 (vec (range (Integer/parseInt (get arr 0))
                             (+ 1 (Integer/parseInt (get arr 1)))))))

;; Takes two arrays, returns true if one of the arrays fully contains the other
(defn fully-contians? [sections]
  (let [x (first sections)
        y (last sections)]
    (or (every? (set x) y)
        (every? (set y) x))))

(defn part-1
  []
  (reduce (fn [total current]
         (->> (str/split current #",")
              (map #(str/split % #"-"))
              (map inclusive-range)
              (fully-contians?)
              (get {false 0 true 1})
              (+ total))) 0 input))

(defn intersects? [sections]
  (let [x (set (first sections))
        y (set (last sections))]
    (not (empty? (set/intersection x y)))))

(defn part-2
  []
  (reduce (fn [total current]
            (->> (str/split current #",")
                 (map #(str/split % #"-"))
                 (map inclusive-range)
                 (intersects?)
                 (get {false 0 true 1})
                 (+ total))) 0 input))

(comment
  (part-1) ;; 511
  (part-2) ;; 821
  )
