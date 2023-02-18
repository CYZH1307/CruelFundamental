### redis特点
- redis是键值数据库，支持多种数据结构如string，list，dict，set，zset等等
- redis支持持久化，有两种策略可以将内存中的数据保存到硬盘中RDB（内存快照）和AOF（append only file）
- redis数据读写速度非常快，一方面是因为它把数据读取到内存中操作，另外一方面由于它是采用C语言实现的
- redis提供复制功能，支持主从复制，实现多个相同数据的Redis副本，是分布式Redis的基础
- redis使用单线程模型，避免了线程切换和竞态消耗
- redis所有操作都是原子性，支持事务
