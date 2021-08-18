# 分布式一致性与共识算法
- https://draveness.me/consensus/

## CAP 原理
- Consistency 数据一致性
- Availability 服务可用性
- Partition Tolerance 分区容错性
- 只能三选二
- Brewer’s Conjecture and the Feasibility of Consistent, Available, Partition-Tolerant Web Services 

## Byzantine Generals Problem 拜占庭将军问题
- 集群中的节点因为出错而发送错误的消息
- 通讯网络可能会破坏信息
- 致使全体成员协作的策略得到不同的结果，从而破坏了系统的一致性

## Paxos 算法
- 三种身份 Proposer 提议者，Acceptor 接受者，Learner 学习者
