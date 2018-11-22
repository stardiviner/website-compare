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

(defn- get-mainContent-html
  "Get the <div class=\"page_right\" element."
  [html]
  ;; avodid the page is not the nav page situation which `get-page-article-links` and
  ;; `get-total-result-pages` can't work correctly.
  (try
    (html/select html [:div.page_right])
    (catch Exception e
      (println (format "[get-mainContent-html] %s" e)))))

(defn get-page-article-links
  "Get articles list's every article link and title."
  [nav-link]
  ;; TODO: extract sub-nav from the sidebar.
  ;; (html/select (get-mainContent-html (get-html nav-link)) [:ul.pleft_t3])
  
  (map #(let [link  (str website-old-url (first (html/attr-values % :href)))
              title (html/text %)]
          {title link})
       (html/select (get-mainContent-html (get-html nav-link)) [:ul.newsList1 :li :a])))

(comment
  (html/select
   (get-mainContent-html (get-html "http://www.sxti.zj.cn/e/action/ListInfo/?classid=47"))
   [:ul.pleft_t3]))

(defn get-total-result-pages
  "How much result pages?"
  [nav-link]
  (try
    (Integer.
     (:page
      (clojure.walk/keywordize-keys
       (ring.util.codec/form-decode
        (second
         (re-find
          #".*/e/action/ListInfo/index.php\?(.*)"
          (first
           (html/attr-values
            (last (html/select (get-mainContent-html (get-html nav-link)) [:div.yema1 :a]))
            :href))))))))
    (catch NullPointerException e
      (println (format "[get-total-result-pages] %s" nav-link))
      (println (format "[get-total-result-pages] %s" e)))))

(comment
  (get-total-result-pages "http://www.sxti.zj.cn/e/action/ListInfo/?classid=33"))

(defn get-all-article-links
  "Get a nav's all articles link and title with map as return."
  [nav-link]
  (let [totalnum (get-total-result-pages nav-link)]
    (if-not (nil? totalnum) ; `totalnum` valid?
      (try
        (first
         (for [n (range 1 (inc totalnum))]
           (let [url   (str nav-link "&page=" n)
                 links (get-page-article-links url)]
             ;; some pages like "http://www.sxti.zj.cn/html/exemplary/about.html" return empty
             ;; articles link. Because they are not article links result page.
             (if-not (empty? links)
               links))))
        (catch NullPointerException e
          (println (format "[get-all-article-links] %s" nav-link))
          (println (format "[get-all-article-links] %s" e)))))))

;; "http://www.sxti.zj.cn/e/action/ListInfo/index.php?classid=33&page=1&totalnum=1648"

(comment
  (get-all-article-links "http://www.sxti.zj.cn/e/action/ListInfo/?classid=19"))

