### 什么是Zookeeper？

ZooKeeper: A Distributed Coordination Service for Distributed Applications

Zookeeper主要用于服务分布式系统，用zookeeper来做统一的配置管理、统一命名服务、分布式锁、集群管理等等。

Zookeeper的数据结构，可以看做一个树，每个结点为Znode，每个结点可以通过路径来标识。

分布式系统涉及到数据一致性问题，zk使用的是ZAB协议。