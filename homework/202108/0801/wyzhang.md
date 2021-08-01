# 0801 etcd

## 简介

etcd 是 K8S 的主要数据存储系统。它负责存储 K8S 的集群状态。是 K8S 的关键组件。

- etcd 一般包含3到5个节点，通过 raft 算法来选举 leader 

- etcd quorum， 就是说，超过半数的节点数可用，etcd 就可以继续提供服务。所以一般节点数是奇数个数。3个节点，允许挂1个，5个节点，允许挂2个。（跟 ZK 一样）

## 使用场景简介

1. K8S store metadata

2. server discovery

3. leader election: master - slave 模式下， master 是个 single point of failure，一般的做法是  由多个 master，但是只有一个 master 在工作，如果这个 master 挂了，通过etcd的 leader election 选出下一个 master。