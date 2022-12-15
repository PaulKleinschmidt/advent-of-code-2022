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
(println initial-stacks)
(println (last (str/split initial-stacks #"\n")))

(def asd (str/split "[J]             [F] [M]"  #""))


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



(create-initial-map) 
