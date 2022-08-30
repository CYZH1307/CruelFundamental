### 什么是CAP原理，什么是FLP不可能定理？

**FLP不可能原理**
> 现实生活中的系统往往都是异步系统。因为系统中各个节点之间的延时，是否宕机等等都是不确定的。那么，在最小化异步模型系统中，是否存在一个可以解决一致性问题的确定性共识算法？
由Fischer、Lynch和Patterson三位科学家于1985年发表的论文《Impossibility of Distributed Consensus with One Faulty Process》指出：在网络可靠，但允许节点失效（即便只有一个）的最小化异步模型系统中，不存在一个可以解决一致性问题的确定性共识算法（No completely asynchronous consensus protocol can tolerate even a single unannounced process death）。
以上结论被称为FLP不可能原理。该定理被认为是分布式系统中重要的原理之一。
此定理实际上告诉人们，不要浪费时间去为异步分布式系统设计在任意场景下都能实现共识的算法。

**CAP原理**
* Consistency 一致性
* Availability 可用性
* Partition 分区容忍性
* CAP理论概述
* CAP权衡
