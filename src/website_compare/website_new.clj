;; "http://www.sxszjzx.com"
;; "http://zjzx.sxsedu.net"

(ns website-compare.website-new
  (:require [clj-http.client :as http]
            [net.cgrand.enlive-html :as html])
  (:import java.net.URL))

(defonce website-new-url "http://www.sxszjzx.com")

;; (defn get-html
;;   "Get HTML string as result."
;;   [url]
;;   ;; faster, use tagsoup internal. But I don't know how to specify encoding.
;;   (try
;;     (html/html-resource (URL. url))
;;     (catch Exception e
;;       (println (format "Error URL: %s" url))
;;       (println (format "URL error: Unknown Host \n %s" e)))))

(defn get-html
  "Get HTML string as result."
  [url]
  (-> (http/get url {:as "UTF8"})
      :body
      html/html-snippet))

(defonce website-new-html (get-html website-new-url))

  )

