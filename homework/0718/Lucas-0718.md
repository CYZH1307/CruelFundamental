# Redis的集合有没有限制，限制是多少
- TODO: https://database.51cto.com/art/201910/604707.htm

## 简介
- Redis，Remote Dictionary Server
- 键值内存数据库，定期异步把数据同步到磁盘
- 每秒钟可以处理10万次读写操作
- Memcacheda只支持字符串，Redis支持String，List，Set，Sorted Set，Hash
- Memcached单个数据只能保存1MB，Redis可以1GB
- 因此Redis的List可以用来做FIFO双向链表，实现一个轻量级的消息队列
- Redis的Set可以做高性能的标签系统
- 受物理内存限制，不能做海量数据处理，只能在较小数据集上做高性能计算

## TODO
- 淘汰策略
- 为什么不用红黑树
- 介绍HyperLogLog
- 如何持久化
- 集群方案
- 适用场景
- 为何有16384个槽
- Redis的数据一致性
- Rdis的分区策略
- 如何适用多核CPU
