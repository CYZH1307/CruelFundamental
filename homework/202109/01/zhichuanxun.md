# 你根据什么来决定你的项目用sql还是nosql database

ref: https://zhuanlan.zhihu.com/p/84269755

## RDS 缺点

大数据 I/O较高
存的是行记录，没办法存数据结构
扩展不方便，会锁表
全文检索较弱
难以处理复杂的数据结构

## Nosql

### 列数据库

Hbase bigtable

规范化压缩

写密集应用，IM...

### KV

Cassandra, redis, levelDB

性能高
ACID

适合，配置参数

### Document

MongoDB CouchDB

修改schema简单
内容

### Fulltext searching

Solr， elastic search Lucene

海量查询
可扩展
Lucene会将索引加到内存中

### graph

neo4j

社交场景，推荐

## 总结

需要考虑
1. 数据量
2. 并发量
3. 实时性
4. 一致性
5. 读写分布
6. 安全性
7. 运维成本

数据量少，并发小： 关系型
大流量，前台内存型（单品页），后台关系型
日志，列，全文检索
搜索系统，全文检索+ 关系
事务型： 关系型+缓存+一致性协议
离线计算：列，关系型
实施计算：内存，列