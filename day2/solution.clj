(ns day2.solution
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(def input (as-> (slurp "day2/input.txt") $
             (str/split $ #"\n")))

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

;; Part 1: Calculate score of given rounds
;; A, X = rock
;; B, Y = paper
;; C, Z = scissors
(defn part-1
  []
  (reduce (fn [points current]
            (calculate-points (str (get current 2))
                              (str (get current 0))
                              points)) 0 input))

;; Inverting winning hands map to get map of winning opponent hands
(def opponent-losing-hands (set/map-invert winning-hands))
(def opponent-draw-hands (set/map-invert draw-hands))
(def opponent-winning-hands {"A" "Z"
                             "B" "X"
                             "C" "Y"})

(defn get-my-hand
  [outcome opponent-hand]
  (case outcome
    ;; If X, get my losing hand
    "X" (get opponent-winning-hands opponent-hand)
    ;; if Y, draw
    "Y" (get opponent-draw-hands opponent-hand)
    ;; if Z, get winning hand
    "Z" (get opponent-losing-hands opponent-hand)))

;; Part 2: Second column in input = the outcome of the round
;; X = I lose
;; Y = Draw
;; Z = I win
;; Get my hand and calculate score of rounds
(defn part-2
  []
  (reduce (fn [points current]
            (let [outcome (str (get current 2))
                  opponent-hand (str (get current 0))
                  my-hand (get-my-hand outcome opponent-hand)]
              (calculate-points my-hand
                                opponent-hand
                                points))) 0 input))

(comment
  (part-1) ;; 12794
  (part-2) ;; 14979
)
