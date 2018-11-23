(ns website-compare.store
  (:require [clojure.java.jdbc :as jdbc])
  (:require [website-compare.crawler :as crawler]))

(def sqlite
  {:dbtype "sqlite"
   :dbname "db.sqlite"}
  ;; {:classname   "org.sqlite.JDBC"
  ;;  :subprotocol "sqlite"
  ;;  :subname     "db.sqlite"}
  )

(comment
  (jdbc/query sqlite "SELECT 1+1 as result"))
