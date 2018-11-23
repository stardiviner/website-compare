(ns website-compare.crawler
  (:require [taoensso.carmine :as redis])

;;; Redis store crawler links
(defonce redis-conn-pool {:pool {}
                          :spec {:host "127.0.0.1" :port 6379}})

;;; define a macro for binding Redis connection pool as default environment.
(defmacro wcar*
  [& body]
  `(redis/wcar redis-conn-pool ~@body))

(comment
  (wcar*
   (redis/ping)))

