(def jobs (load-jobs "/local/path/to/hadoop/job-history-root-dir"))
(defn getJobData [user] (map #(vals %) (map #(select-keys % [:mapred.reduce.tasks, :mapred.map.tasks, :mapred.job.name, :finish-time, :launch-time, :submit-time]) (filter #(= (:user.name % 0) user) jobs))))
(def mrdata (getJobData "eselyavka"))
(use 'clojure.java.io)
;write csv file epoch with ms
(with-open [wrtr (writer "/tmp/test.txt")](doseq [i mrdata] (.write wrtr (apply str (interpose ";"  i) ))(.write wrtr "\n")))
;write csv file, submit time is epoch without ms
;(with-open [wrtr (writer "/tmp/test.txt")](doseq [i mrdata] (.write wrtr (apply str (interpose ";" (cons (quot (first i ) 1000) (rest i) ) ) ))(.write wrtr "\n")))
