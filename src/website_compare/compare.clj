(ns website-compare.compare
  (:require [website-compare.crawler :as crawler])
  (:require [website-compare.website-old :as old])
  (:require [website-compare.website-new :as new])
  (:require [taoensso.carmine :as redis]))

;;; Compare through title. if old website's article title exist in new website's
;;; title database, then it is migrated. Otherwise write the missing article title and
;;; link to a log file.
(defn compare-titles
  []
  (crawler/crawl-website-new-links)
  (for [old-link (crawler/all-links-in-redis :old/links)]
    (let [title (:title (old/parse-article-title-and-content old-link))]
      (redis/exists title))))
