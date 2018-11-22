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

;;; Nav sections
(defonce nav-bar
  (drop 1 (html/select
           website-new-html
           [:html :body :div.wrap :div.nav
            :div.siteWidth :ul#mainNav.mainNav
            :li])))

;;; NOTE: Thise `(html/select % [:a])` is really smart.
(defonce nav-bar-links-map
  (map #(let [nav   (-> (html/select % [:a])
                        first)
              link  (str website-new-url (first (html/attr-values nav :href)))
              title (html/text nav)]
          {title link})
       nav-bar))

(defn- get-mainContent-html
  "Get the <div class=\"mainContent\"> element."
  [html]
  (try
    (html/select html [:div.mainContent])
    (catch NullPointerException e
      (println e))))

(defn get-page-article-links
  "Get articles list's every article link and title."
  [nav-link]
  (map #(let [link  (str website-new-url (first (html/attr-values % :href)))
              title (html/text %)]
          {title link})
       (html/select (get-mainContent-html (get-html nav-link))
                    [:div.mBd :ul.pageTPList :li :div.title :a.tit])))

(comment
  (map #(let [link  (str website-new-url (first (html/attr-values % :href)))
              title (html/text %)]
          {title link})
       (html/select (get-mainContent-html (get-html "http://www.sxszjzx.com/ztlm/smgc"))
                    [:div.mBd :ul.pageTPList :li :div.title :a.tit])) ; ()
  (empty?
   (html/select
    (get-mainContent-html (get-html "http://www.sxszjzx.com/ztlm/smgc"))
    [:div.mBd :ul.pageTPList :li :div.title :a.tit])) ; ()
  )

(defn get-total-result-pages
  "How much result pages?"
  [nav-link]
  (try
    (Integer.
     (second
      (re-find #"/共(.*)页"
               (html/text
                (first (html/select (get-mainContent-html (get-html nav-link))
                                    [:div.mBd :div.page :span.total]))))))
    (catch Exception e
      (println nav-link)
      (println e))))

(comment
  (get-total-result-pages "http://www.sxszjzx.com/xydt/xyxw"))

