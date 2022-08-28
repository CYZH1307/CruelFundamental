C：Consistency 一致性：同一数据的多个副本是否实时相同。all nodes see the same data at the same time

A：Availability 可用性：一定时间内，系统能返回一个明确的结果 则称为该系统可用。Reads and writes always succeed

P：Partition tolerance 分区容错性：分布式系统在遇到某节点或网络分区故障的时候，仍然能够对外提供满足一致性和可用性的服务。the system continues to operate despite arbitrary message loss or failure of part of the system

FLP定理[1]是分布式理论中最重要的理论之一，它指出在最小化异步网络通信场景下，即使只有一个节点失败，也没有一种确定性的共识算法能够使得在其他节点之间保持数据一致。
