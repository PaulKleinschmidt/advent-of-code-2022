(ns day2.solution
  (:require [clojure.string :as str]))

(def input (as-> (slurp "day2/input.txt") $
             (str/split $ #"\n")))

(def test-input (as-> (slurp "day2/test.txt") $
             (str/split $ #"\n")))
;; A, X = rock
;; B, Y = paper
;; C, Z = scissors
(def winning-hands {"X" "C"
                    "Y" "A"
                    "Z" "B"})

(def draw-hands {"X" "A"
                 "Y" "B"
                 "Z" "C"})
(defn won-round? 
  [my-hand opponent-hand]
  (= (get winning-hands my-hand) opponent-hand))

(defn draw-round?
  [my-hand opponent-hand]
  (= (get draw-hands my-hand) opponent-hand))

(defn calculate-points
  [my-hand opponent-hand points] 
  (cond-> points
    ;; if won round, + 3 points
    (won-round? my-hand opponent-hand) (+ 6)
    ;; if draw, + 1 point
    (draw-round? my-hand opponent-hand) (+ 3)
    ;; if I played rock, + 1 point
    (= my-hand "X") (+ 1)
    ;; if I played paper, + 2 points
    (= my-hand "Y") (+ 2)
    ;; if I played scissors, + 3 point
    (= my-hand "Z") (+ 3)))

(defn part-1
  []
  (reduce (fn [points current]
            (calculate-points (str (get current 2))
                              (str (get current 0))
                              points)) 0 input))


(comment 
  (part-1) ;; 12794
  )
