# MySQL有哪几种高可用方案？

1. 主从结构

可以使读写分离: 主库提供写服务, 从库提供读服务, 使用`bin log`实现主从同步.

2. 主主 + Keepalived

两库互为主从, 主库对外提供服务, 另一个库作为备份从库. 使用`Keepalived`监测主库是否存活, 如果主库宕机, 则由`Keepalived`切换从库为主库. 

3. MySQL Group Replication + InnoDB Cluster

MGR（MySQL Group Replication）使用 GCS (Group Communication System) 协议同步数据, GCS 可保证消息的原子性. MGR 配合 InnoDB Cluster 实现高可用.

InnoDB Cluster 包含以下组件:

- MySQL Shell. 用于管理MGR集群
- Gropu Replication. 提供一组高可用的MySQL实例.
- MySQL Router. 在应用程序和集群之间提供透明的路由. 当MGR发生切时, 自动路由到新的MGR主节点.

这种方式只支持InnoDB表. 


