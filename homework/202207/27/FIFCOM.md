[//]: fcn.ign
## 介绍一下GFS, Big Table 和 MapReduce
这三个都是由Google开发的。

#### GFS

> GFS是面向大规模数据密集型应用的、可伸缩的分布式文件系统。它拥有高可靠性、高扩展性、高效性和高容错性。它为大数据的存储而生。

一个GFS Cluster包含单个(逻辑上的)Master节点和多个Chunk节点。

GFS中的文件会被分割为固定大小的Chunk，并为Chrunk分配UUID。为了可靠性，每个Chunk都会被复制到至少三台Chunk节点上。

这些Chunk都会由Master节点来管理：元数据包括namespace、文件和chunk映射信息。

#### BigTable

> Bigtable 是一个分布式的结构化数据存储系统，它被设计用来处理海量数据：通常是分布在数千台普通服务器上的 PB 级的数据。

换句话说，BigTable是基于LSM Tree的分布式NoSQL数据库。可存储键值对，其键值均为byte类型（好像HBase是string类型？）。

BigTable的逻辑结构主要有
1. 行键：对应关系数据库中的主键，唯一标示一行记录
2. 时间戳：在单元格中可以存放多个版本的数据
3. 列族：就是一系列“列”的集合，列族以单独的文件进行存储，必须在使用前定义。

此外，关于LSM Tree，即日志结构合并树，大多数NoSQL的核心思想都是基于LSM来做的

特点：
1. 利用顺序写来提高写性能，代价是牺牲了部分读性能
2. 插入数据时首先插入到内存中，内存大小达到阈值或每隔一段时间将内存中的数据顺序写入到磁盘中

#### MapReduce

MapReduce是一个编程框架，它允许我们在分布式环境中对大数据集进行分布式并行处理

MapReduce包含两个完全不同的任务-Map和Reduce

正如MapReduce名字所暗示的，Reducer阶段是在Mapper阶段完成之后进行。

因此，首先是map作业，它会读取数据块并进行处理然后产生key-value对作为中间输出结果

Mapper或者说是map作业的结果（key-value对）将作为Reducer的输入

reducer从多个map作业处接收key-value对

然后reducer将这些中间数据（中间key-value对）合并成更小的元组或者key-value对作为最终输出。
