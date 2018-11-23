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

;;; create tables
(defn create-table
  "Create corresponding table with passed in argument symbol as table name."
  [table-name]
  (jdbc/with-db-connection [db sqlite]
    (jdbc/db-do-commands
     db
     (jdbc/create-table-ddl table-name
                            {:id      "INTEGER PRIMARY KEY AUTOINCREMENT"
                             :title   "TEXT"
                             :article "TEXT"}
                            {:conditional? true} ; table existence check
                            ))))

