# 深度解读：Kafka 放弃 ZooKeeper，消息系统兴起二次革命
- https://www.infoq.cn/article/phf3gfjutdhwmctg6kxe

## 原因
- 2021.3.30，Kafka 供应商 Confluent 宣布 Kafka 2.8 可以不需要 ZooKeeper 运行
- ZooKeeper 将会被基于 Kafka Raft 的 Quorm 控制器替代
- 维护两个集群，ZooKeeper 和 Kafka 让系统复杂度增加，运维繁重
- 限制了 Kafka 在轻量级环境下的应用
- ZooKeeper 的分区特性，限制了 Kafka 的承载能力

## 以前
- 依赖单一控制节点来处理ZK集群的读写，本地缓存ZK的元数据
- 元数据增加，增加ZK集群的压力，导致Watch的延迟
- 节点发生变化，需要很多ZK操作，比如主节点分区迁移到其他节点

## 现在
- Quorum 控制器使用 KRaft 协议，和ZK的ZAB协议，Raft协议有点相像
- 仲裁节点成为主节点之前不需要到ZK加载状态，Kafka 运维量减半
