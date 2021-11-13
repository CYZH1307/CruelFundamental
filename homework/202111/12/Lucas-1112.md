# Spark 原理
- https://liam-blog.ml/2019/10/23/spark-core-rdd/
- Spark 核心，API to define RDD, fundamental of other Spark libraries
- Spark SQL, from Apache Hive SQL, operate on RDD
- Spark Streaming, let application operate real-time data like RDD
- MLlib, machine learning on RDD
- GraphX, extend RDD API, create graph, visit vetex

- Manager
- Worker: Driver or Executor
- Driver: Application.main()
- Driver: a process to execute Application on worker

- RDD, Resilient Distributed Dataset 弹性分布式数据集
- Two operations: Transformation and Action
- In Memory, Immutable, Parallel
- Composite of several partitions, calculation function, parent RDD dependency, partitioner
- RDD Object -> DAG Graph -> DAG Scheduler --> TaskSet -> Task Schedule
- TODO: P267
