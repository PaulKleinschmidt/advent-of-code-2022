(ns utils
  (:require [clojure.string :as str]))

(defn parse-file
  [file]
  (as-> (slurp file) $
    (str/split $ #"\n")))
