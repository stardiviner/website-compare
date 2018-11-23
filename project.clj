(defproject website-compare "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 ;; HTTP
                 [clj-http "2.2.0"]
                 ;; HTML
                 [enlive "1.1.6"]
                 [clj-tagsoup "0.3.0"]
                 ;; Selenium
                 [etaoin "0.2.9" :exclusions [clj-http]]
                 
                 [clj-webdriver "0.7.2"
                  :exclusions  [org.seleniumhq.selenium/selenium-server
                                org.seleniumhq.selenium/selenium-java
                                org.seleniumhq.selenium/selenium-remote-driver]]
                 ;; [org.seleniumhq.selenium/selenium "2.0rc2"]
                 [org.seleniumhq.selenium/selenium-remote-control "2.0rc2"]
                 [org.seleniumhq.selenium/selenium-remote-client "2.0b1"
                  :exclusions [org.apache.httpcomponents/httpclient]]
                 
                 [org.seleniumhq.selenium/selenium-server "3.14.0"]
                 [org.seleniumhq.selenium/selenium-support "3.14.0"]
                 [org.seleniumhq.selenium/selenium-api "3.14.0"]
                 ;; [org.seleniumhq.selenium/selenium-parent "2.53.1"]
                 [org.seleniumhq.selenium/selenium-java "3.14.0"]
                 
                 [org.seleniumhq.selenium/selenium-remote-driver "3.14.0"]
                 [org.seleniumhq.selenium/selenium-firefox-driver "3.14.0"]
                 [org.seleniumhq.selenium/selenium-chrome-driver "3.14.0"]
                 [org.seleniumhq.selenium/selenium-edge-driver "3.14.0"]
                 [org.seleniumhq.selenium/selenium-ie-driver "3.14.0"]
                 [org.seleniumhq.selenium/selenium-safari-driver "3.14.0"]
                 
                 [org.seleniumhq.selenium/selenium-iphone-driver "2.39.0"]
                 [org.seleniumhq.selenium/selenium-android-driver "2.39.0"]
                 [org.seleniumhq.selenium/selenium-android-client "2.0b1"]
                 
                 [org.seleniumhq.webdriver/webdriver-support "0.9.7376"]
                 [org.seleniumhq.webdriver/webdriver-selenium "0.9.7376"]
                 [org.seleniumhq.webdriver/webdriver-ie "0.9.7376"]
                 [org.seleniumhq.webdriver/webdriver-chrome "0.9.7376"]
                 [org.seleniumhq.webdriver/webdriver-firefox "0.9.7376"]
                 [org.seleniumhq.webdriver/webdriver-remote-client "0.9.7376"]
                 [org.seleniumhq.webdriver/webdriver-remote-server "0.9.7376"]

                 ;; Web Crawlers
                 [pegasus "0.7.0"]
                 [org.clojars.kokos/scrap "0.2.2a"]
                 ;; XML
                 [org.clojure/data.xml "0.0.8"]
                 ;; Encoding
                 [org.webjars.npm/iconv "2.2.0"]
                 ;; Digest
                 [digest "1.4.8"]
                 ;; Redis
                 [com.taoensso/carmine "2.18.1"]
                 ;; JDBC
                 [org.clojure/java.jdbc "0.7.8"]
                 ;; SQLite
                 [org.xerial/sqlite-jdbc "3.23.1"]
                 ;; MongoDB
                 [congomongo "1.0.1"]
                 [com.novemberain/monger "3.1.0"]
                 
                 ;; Validate

                 ;; Compare

                 ;; SimHash

                 ;; Analysis
                 [incanter/incanter "1.9.3"]
                 
                 [com.cemerick/pomegranate "1.0.0"] ; add-dependencies
                 ])
