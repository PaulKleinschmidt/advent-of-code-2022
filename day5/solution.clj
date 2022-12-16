(ns day5.solution
  (:require [clojure.string :as str]
            [utils :refer [parse-file]]
            [clojure.math :as Math]))

(def input (parse-file "day5/input.txt" #"\n\n"))
(def initial-stacks (first input))
(def instructions (last input))

(defn create-initial-crate-map []
  (let [stacks (str/split initial-stacks #"\n")
        empty-map {"1" [] "2" [] "3" [] "4" [] "5" [] "6" [] "7" [] "8" [] "9" []}]
    (->> (butlast stacks) 
         (vec)
         ;; need to reverse stacks to ensure crates on top are at the end of vector
         (reverse)
         (map (fn [x] (as-> x $
                        (str/split $ #" ")
                        (reduce-kv (fn [crate-map i current]
                                     (if (not (= current ""))
                                       (let [crate-pos (->> (subvec $ 0 i)
                                                            (map #(if (= % "") 0.25 1))
                                                            (apply + 1)
                                                            (Math/round)
                                                            (str))
                                             crate-val (subs current 1 2)
                                             existing-crates (get crate-map crate-pos)]
                                         (assoc crate-map crate-pos (conj existing-crates crate-val)))
                                       crate-map)) empty-map $))))
         (reduce (fn [total current]
                   (merge-with into total current)) {})
         (into (sorted-map)))))

(defn parse-int [s]
  (when (re-find #"\A-?\d+" s)
    (Integer/parseInt s)))

(defn parse-instructions-line [str]
  (as-> str $
    (str/split $ #" ")
    (filter #(parse-int %) $)
    (vec $)))

(defn operate-crane
  [keep-order]
  (let [initial-map (create-initial-crate-map)
        instructions-list (str/split instructions #"\n")]
    (->> instructions-list
         (map #(parse-instructions-line %))
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
                                                     ;; For part 2, retain order of crates when moving
                                                     (#(if keep-order % (reverse %)))
                                                     (conj destination)
                                                     (flatten)
                                                     (vec))
                            source-key (vec (drop-last amount source))))) initial-map)
         (vals)
         (map #(last %))
         (str/join))))

(comment 
  ;; Part 1
  (operate-crane false) ;; GFTNRBZPF
  ;; Part 2
  (operate-crane true) ;; VRQWPDSGP
  )
