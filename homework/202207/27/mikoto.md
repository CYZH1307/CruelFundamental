## 介绍一下GFS MapReduce 和 BigTable

Google的三大数据处理系统，

### GFS google文件系统
GFS 是个大型的 分布式问津系统，为GOOLE大数据处理系统提供海量的存储，

GFS 具有以下特点：
1. 采用中心服务器模式，可以方便增加chunk server Master Server可以掌握系统内所有chunk server的情况，方便进行负载均衡，不存在元数据的一致性问题。
2. 不缓存数据，保证一致性

chunkServer在硬盘上存储数据，每个数据块大小为64MB，每个数据被复制成三个副本被放到不同的server中

### MapReduce
GFS 解决Google海量数据的存储，而MapReduce是为了如何从海量数据中快速计算并获取期望结果

MapReduce 是由google开发的针对大规模群组中的海量数据处理的分布式编程模型。

MapReduce 实现了Map和Reduce两个功能 Map吧一个函数应用于集合中的所有成员，然后返回一个基于这个处理结果的结果集， 而Reduce是把两个或者多个Map通过多线程进程或者独立系统进行分类和归纳。

### BigTable

是Google设计的分布式数据存储系统，是用来处理海量数据的非关系型数据库，BigTable一个稀疏的，分布式的，持久化存储的多维度排序的隐射表。

具有广泛的适用性，很强的可扩展性，很高的可用性以及底层系统的简单性`
