;;; http://www.sxti.zj.cn

(ns website-compare.website-old
  (:require [clj-http.client :as http]
            [net.cgrand.enlive-html :as html])
  (:import java.net.URL)
  (:require [ring.util.codec :only [form-decode]])
  (:require [clojure.walk :only [keywordize-keys]]))

(defonce website-old-url "http://www.sxti.zj.cn")

;;; Selectors
(def selector-nav [:div.head_3 :ul#head_nav])
(def selector-content [:div.page_1])
(def selector-sidebar [:div.page_left :div.pleft_t3])
(def selector-article [:div.page_right :div.pright_t3])

(defn get-html
  "Get HTML string as result."
  [url]
  (try
    (-> (http/get url {:as "GB2312"})
        :body
        html/html-snippet)
    (catch java.net.UnknownHostException e
      (println (format "[get-html] Error URL: %s" url))
      (println (format "[get-html] %s" e)))))

(defonce website-old-html (get-html "http://www.sxti.zj.cn/"))

;;; Nav sections
(defonce nav-bar
  (html/select
   (drop 1
         (first (map #(html/select % [:li])
                     ;; nav bar
                     (html/select
                      website-old-html
                      [:html :body :div.page_all :div.head_2 :div.head_3 :ul#head_nav]))))
   [:a]))

(defonce nav-bar-links-map
  (map #(let [link  (str website-old-url
                         ;; :attrs nil (:href does not exist)
                         (if (nil? (:attrs %))
                           nil
                           ;; :href "javascript:void(0)"
                           (if (= (first (html/attr-values % :href)) "javascript:void(0)")
                             nil
                             ;; :href "/..."                            
                             (first (html/attr-values % :href)))))
              title (html/text %)]
          {title link})
       nav-bar))

