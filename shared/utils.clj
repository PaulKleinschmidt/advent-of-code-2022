(ns utils
  (:require [clojure.string :as str]))

(defn parse-file
  ([file] (parse-file file #"\n"))
  ([file delimiter]
   (as-> (slurp file) $
     (str/split $ delimiter))))
