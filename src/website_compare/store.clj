(ns website-compare.store
  (:require [clojure.java.jdbc :as jdbc])
  ;; (:require [somnium.congomongo :as mongodb])
  (:require [monger.core :as mongodb]))

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
                             :content "TEXT"}
                            {:conditional? true} ; table existence check
                            ))))

(defn save-to-sqlite
  "Save data into SQLite DB."
  [table title content]
  (create-table table)
  (jdbc/with-db-connection [db sqlite]
    ;; NOTE: No need to insert the `:id`.
    (jdbc/insert! db table {:title   title
                            :content content})))
