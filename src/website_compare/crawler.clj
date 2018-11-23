(ns website-compare.crawler
  (:require [clj-http.client :as http]
            [net.cgrand.enlive-html :as html])
  (:import java.net.URL)
  (:require [taoensso.carmine :as redis])
  (:require [website-compare.website-old :as old])
  (:require [website-compare.website-new :as new])
  (:require [website-compare.store :as store]))

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

;;;crawl links

(defn crawl-website-old-links []
  "Crawl all links of website old."
  (for [nav-link (into #{}            ; use set to remove duplicate links.
                       (map #(-> % vals first) old/nav-bar-links-map))]
    (for [article-link (old/get-all-article-links nav-link)]
      (wcar*
       ;; clean up all links at firts.
       (redis/del :old/links)
       (redis/lpush :old/links article-link)))))

(comment
  (crawl-website-old-links))

(comment
  (wcar* (redis/hvals :old/links)))

(defn crawl-website-new-links []
  "Crawl all links of website new."
  ;; clean up all links at firts.
  (wcar* (redis/del :new/links))
  (for [nav-link (into #{}            ; use set to remove duplicate links.
                       (map #(-> % vals first) new/nav-bar-links-map))]
    (for [article-links-in-a-page (new/get-all-article-links nav-link)]
      (for [article-link article-links-in-a-page]
        (wcar* (redis/lpush :new/links (first (vals article-link))))))))

(comment
  (crawl-website-new-links))

(comment
  (wcar* (redis/get :new/links)))

;;; crawl articles

(defn all-links-in-redis
  "Get all links in Redis."
  [website-key]
  (wcar*
   ;; (redis/hgetall website-key)
   (redis/hvals website-key)))

(defn crawl-website-old-articles []
  "Crawl all articles of website old."
  (crawl-website-old-links)
  (for [link (all-links-in-redis :old/links)]
    (let [content (old/parse-article-title-and-content link)]
      (store/save-to-sqlite :old (:title content) (:content content)))))

(defn crawl-website-new-articles []
  "Crawl all articles of website new."
  (crawl-website-new-links)
  (for [link (all-links-in-redis :new/links)]
    (let [content (new/parse-article-title-and-content link)]
      (store/save-to-sqlite :new (:title content) (:content content)))))

