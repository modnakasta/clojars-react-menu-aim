(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.13" :scope "test"]
                  [cljsjs/boot-cljsjs "0.5.2"  :scope "test"]
                  [cljsjs/react       "15.3.0-0"]
                  [cljsjs/react-dom   "15.3.0-0"]])


(require
  '[adzerk.bootlaces :refer :all]
  '[cljsjs.boot-cljsjs.packaging :refer :all])


(def +lib-version+ "1.0.1")
(def +version+ (str +lib-version+ "-0"))


(bootlaces! +version+)


(task-options!
  pom {:project     'ua.modnakasta/react-menu-aim
       :version     +version+
       :description "React menu aim."
       :url         "https://github.com/jasonslyvia/react-menu-aim"
       :scm         {:url "https://github.com/modnakasta/clojars-react-menu-aim"} ; mk link
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})


(deftask package []
  (comp
    (download :url      "https://raw.githubusercontent.com/jasonslyvia/react-menu-aim/7b12d23d31881fe0eef2b8bfb23b4d7bd7cc76f1/index.js" ;(format "https://github.com/jasonslyvia/react-menu-aim/archive/%s.zip" +lib-version+)
              :checksum "71B04E7F61460D645B5547E07E13B932")

    (sift :move {#"index.js"
                 "mk/react-menu-aim/development/react-menu-aim.inc.js"})

    (minify :in   "mk/react-menu-aim/development/react-menu-aim.inc.js"
            :out  "mk/react-menu-aim/production/react-menu-aim.min.inc.js"
            :lang :ecmascript5)

    (sift :include #{#"^mk"})

    (deps-cljs :name "mk.react-menu-aim"
               :requires ["cljsjs.react"])

    (pom)

    (jar)))


(deftask clojars []
  (comp
    (package)
    (push-release)))
