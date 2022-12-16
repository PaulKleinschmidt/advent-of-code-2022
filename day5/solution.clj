(ns day5.solution
  (:require [clojure.string :as str]
            [utils :refer [parse-file]]
            [clojure.math :as Math]))

(def input (parse-file "day5/input.txt" #"\n\n"))
(def initial-stacks (first input))
(def instructions (last input))

(defn create-initial-map []
  (let [stacks (str/split initial-stacks #"\n")
        empty-map {"1" [] "2" [] "3" [] "4" [] "5" [] "6" [] "7" [] "8" [] "9" []}]
    (->> (vec (butlast stacks)) 
         (reverse)
         (map (fn [x] (as-> x $
                        (str/split $ #" ")
                        (reduce-kv (fn [total i current]
                                     (if (not (= current ""))
                                       (let [crate-pos (->> (subvec $ 0 i)
                                                            (map #(if (= % "") 0.25 1))
                                                            (apply + 1)
                                                            (Math/round)
                                                            (str))
                                             crate-val (subs current 1 2)
                                             existing-crates (get total crate-pos)]
                                         (assoc total crate-pos (conj existing-crates crate-val)))
                                       total)) empty-map $))))
         (reduce (fn [total current]
                   (merge-with into total current)) {})
         (into (sorted-map)))))

(defn parse-int [s]
  (when (re-find #"\A-?\d+" s)
    (Integer/parseInt s)))

(defn get-instructions-line [str]
  (as-> str $
    (str/split $ #" ")
    (filter #(parse-int %) $)
    (vec $)))

(defn part-1
  []
  (let [initial-map (create-initial-map)
        instructions-list (str/split instructions #"\n")]
    (->> instructions-list
         (map #(get-instructions-line %))
         (reduce (fn [acc current]
                   (let [amount (parse-int (first current))
                         source-key (second current)
                         destination-key (last current)
                         source (->> source-key
                                     (get acc))
                         destination (->> destination-key
                                          (get acc))]
                     (assoc acc destination-key (->> source
                                                     (take-last amount)
                                                     (reverse)
                                                     (conj destination)
                                                     (flatten)
                                                     (vec))
                            source-key (vec (drop-last amount source))))) initial-map)
         (vals)
         (map #(last %))
         (str/join))))

(comment 
  (part-1) ;;GFTNRBZPF
  )
