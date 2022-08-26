- 什么是CAP原理，什么是FLP不可能定理？

现实生活中的系统往往都是异步系统。因为系统中各个节点之间的延时，是否宕机等等都是不确定的。那么，在最小化异步模型系统中，是否存在一个可以解决一致性问题的**确定性共识算法**？

由Fischer、Lynch和Patterson三位科学家于1985年发表的论文《Impossibility of Distributed Consensus with One Faulty Process》指出：**在网络可靠，但允许节点失效（即便只有一个）的最小化异步模型系统中，不存在一个可以解决一致性问题的确定性共识算法**（No completely asynchronous consensus protocol can tolerate even a single unannounced process death）。

以上结论被称为**FLP不可能原理。**该定理被认为是分布式系统中重要的原理之一。

此定理实际上告诉人们，不要浪费时间去为异步分布式系统设计在任意场景下都能实现共识的算法。



**CAP的定义**

- **Consistency 一致性**

这里的一致性指的是**强一致性。**

强一致性意味着，当系统的更新操作成功并返回客户端完成后，所有节点**在同一时间的**数据完全一致。

- **Availability 可用性**

指的是分布式系统可以正常响应时间内提供相应的服务。

- **Partition 分区容忍性**

分布式系统在遇到某节点或网络分区故障的时候，仍然能够对外提供满足一致性和可用性的服务。



**CAP理论概述**

一个分布式系统最多只能同时满足一致性（Consistency）、可用性（Availability）和分区容忍性（Partition tolerance）这三项中的两项。



一个正确的分布式算法需要满足两条性质：

Safety：具备Safety性质的算法保证坏的事情绝对不会发生，例如对于满足Safety性质的分布式选主(Leader election)算法，绝对不会出现一个以上进程被选为Leader的情况。
Liveness：具备Liveness性质的算法保证好的事情终将发生，即算法在有限的时间内可以结束。

