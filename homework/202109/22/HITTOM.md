Q: kafka为什么放弃了ZooKeeper
A:
1) 降低运维复杂度,不再需要维护kafka和zookeeper两套集群
2) zookeeper的强一致特性会降低kafka高峰期的性能

剔除zookeeper后,kafka将元数据存储到了自己内部

zookeeper是以fast paxos算法为基础的分布式一致性算法
zookeeper运行流程:
a) 选举leader b) 同步数据 c)大多数机器得到响应并接受leader

