(ns solution
  (:require [clojure.string :as str]))

(defn parse-int [s]
  (if (number? s) s (Integer/parseInt s)))

(defn highest-calories []
  (let [input (as-> (slurp "day1/input.txt") $
                (str/split $ #"\n\n")
                (map #(str/split % #"\n") $))]
    (apply max (map #(reduce (fn [total current] (+ (parse-int total) (parse-int current))) 0 %) input))))


(comment 
  (highest-calories) ;;66487
  )
