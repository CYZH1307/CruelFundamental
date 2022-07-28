### 介绍一下GFS, Big Table & MapReduce



##### GFS：

google file system

分布式+log的存储方式，更多是为了大数据服务

##### Big Table：

分布式的数据库 ，不是很懂



##### MapReduce：

一个分布式模型，作者发现很多任务都可以拆解成Map和reduce两个任务，围绕这两个任务做了分布式的优化，关键点在于怎么做哈希以及Map任务和reduce任务之间的通信，复杂度取决于这两者中的短板(涉及长尾等等问题)。