# 0731 zookeeper
zookeeper 是一个分布式协调服务, 可以在分布式环境下用作配置管理, 命名服务, 分布式锁, 集群管理等等


## 基本架构

既然是分布式环境中的协调者, 那么自身必须要具备 HA 的能力. 所以 zookeeper 是由多台服务器组成一个集群对外提供服务, 不同的服务器有不同的角色: leader, follower, observer 

leader 和 follower 就是分布式环境中的选举的角色, observer可以理解为类似读写分离中的读节点, 不参与选举.

## 基本工作方式

zookeeper 主要通过 树形文件系统 (每个节点znode) 和 通知机制 来实现其对应的功能

类似 observer pattern, 客户端可以订阅 zookeeper 中的 znode, 当znode 内容发生变化时, 所有的 subscriber 会被通知到, 从而做出一些相应的操作

## 选举与数据同步

zookeeper 使用 zab 协议进行崩溃恢复和 数据同步, 主要方式为广播的方式.
