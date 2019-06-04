(ns leiningen.new.webapp
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "webapp"))

(defn webapp
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' webapp project.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             [".gitignore" (render "gitignore" data)]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["src/{{sanitized}}/config.clj" (render "config.clj" data)]
             ["src/{{sanitized}}/utils.clj" (render "utils.clj" data)]
             ["src/{{sanitized}}/execution.clj" (render "execution.clj" data)]
             ["src/{{sanitized}}/handlers.clj" (render "handlers.clj" data)]
             ["src/{{sanitized}}/router.clj" (render "router.clj" data)]
             ["src/{{sanitized}}/types.clj" (render "types.clj" data)]
             ["src/{{sanitized}}/commands/core.clj" (render "commands/core.clj" data)]
             ["src/{{sanitized}}/database/core.clj" (render "database/core.clj" data)]
             ["src/user.clj" (render "user_main.clj" data)]
             ["dev/user.clj" (render "user_dev.clj" data)]
             ["test/{{sanitized}}/core_test.clj" (render "test/core_test.clj" data)]
             ["test/{{sanitized}}/database_test.clj" (render "test/database_test.clj" data)]
             ["test/{{sanitized}}/test_utils.clj" (render "test/test_utils.clj" data)]
             ["migrations/init.sql" (render "migrations/init.sql" data)]
             ["migrations/20190420123030_init.up.sql" (render "migrations/init.up.sql" data)]
             ["migrations/20190420123030_init.down.sql" (render "migrations/init.down.sql" data)]
             ["migrations/20190420123031_truncate-fn.up.sql" (render "migrations/truncate-fn.up.sql" data)]
             ["migrations/20190420123031_truncate-fn.down.sql" (render "migrations/truncate-fn.down.sql" data)]
             ["migrations/20190420123032_user-auth.up.sql" (render "migrations/user-auth.up.sql" data)]
             ["migrations/20190420123032_user-auth.down.sql" (render "migrations/user-auth.down.sql" data)]
             ["resources/simplelogger.properties" (render "simplelogger.properties" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["README.md" (render "README.md" data)]
             ["Dockerfile" (render "Dockerfile" data)]
             [".env" (render "env" data)])))
