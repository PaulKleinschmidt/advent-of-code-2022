(ns solution
  (:require [clojure.string :as str]))

(defn parse-int [s]
  (if (number? s) s (Integer/parseInt s)))

(def input (as-> (slurp "day1/input.txt") $
             (str/split $ #"\n\n")
             (map #(str/split % #"\n") $)))

(def totals (map #(reduce (fn [total current] (+ (parse-int total) (parse-int current))) 0 %) input))

(defn part-1 []
  (apply max totals))

(defn part-2 []
  (->> totals
       sort
       (take-last 3)
       (apply +)))

(comment 
  (part-1) ;; 66487
  (part-2) ;; 197301
  )
