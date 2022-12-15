(ns day5.solution
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [utils :refer [parse-file]]
            [clojure.math :as Math]))

(defn parse-file1
  [file]
  (as-> (slurp file) $
    (str/split $ #"\n\n")))

(def input (parse-file1 "day5/input.txt"))
(def initial-stacks (first input))
(def instructions (last input))
(println instructions)
(println (last (str/split initial-stacks #"\n")))

(defn create-initial-map []
  (println "BUTLAST" initial-stacks)
  (let [stacks (str/split initial-stacks #"\n")]
    (->> (vec (butlast stacks))
         (map (fn [x] (as-> x $
                        (str/split $ #" ")
                        (replace {"" "X"} $)
                        (reduce-kv (fn [total i current]
                                     (if (not (= current "X"))
                                       (let [crate-pos (->> (subvec $ 0 i)
                                                            (map #(if (= % "X") 0.25 1))
                                                            (apply + 1)
                                                            (Math/round)
                                                            (str))
                                             crate-val (subs current 1 2)
                                             existing-crates (get total crate-pos)]
                                         (assoc total crate-pos (conj existing-crates crate-val)))
                                       total)) {"1" [] "2" [] "3" [] "4" [] "5" [] "6" [] "7" [] "8" [] "9" []} $))))
         (reduce (fn [total current]
                   (merge-with into total current)) {})
         (into (sorted-map)))))

(defn parse-int [s]
  (when (re-find #"\A-?\d+" s)
    (Integer/parseInt (re-find #"\A-?\d+" s))))

(defn get-instructions-line [str]
  (as-> str $
    (str/split $ #" ")
    (filter #(parse-int %) $)
    (vec $)))

(defn part-1
  []
  (let [initial-map (create-initial-map)
        instructions-list (str/split instructions #"\n")]
    (println "initial map" initial-map)
    (println "instructions" instructions-list)
    (->> instructions-list
         (map #(get-instructions-line %)))))


(as-> "move 2 from 4 to 6" $
  (str/split $ #" ")
  (filter #(parse-int %) $)
  (vec $))




(parse-int "123")

(def b (every? #(Character/isDigit %) "a"))
(println b)
(str/split "move 2 from 4 to 6" #"move ")



(part-1)
(create-initial-map)
