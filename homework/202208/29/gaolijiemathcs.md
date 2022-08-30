### 什么是Raft算法？

raft是工程上使用较为广泛的强一致性、去中心化、高可用的分布式协议。

Raft算法的头号目标就是容易理解（UnderStandable），这从论文的标题就可以看出来。当然，Raft增强了可理解性，在性能、可靠性、可用性方面是不输于Paxos的。

为了达到易于理解的目标，raft做了很多努力，其中最主要是两件事情：

- 问题分解：问题分解是将"复制集中节点一致性"这个复杂的问题划分为数个可以被独立解释、理解、解决的子问题。在raft，子问题包括，*leader election*， *log replication*，*safety*，*membership changes*。
- 状态简化：状态简化对算法做出一些限制，减少需要考虑的状态数，使得算法更加清晰，更少的不确定性（比如，保证新选举出来的leader会包含所有commited log entry）

 raft协议中，一个节点任一时刻处于以下三个状态之一：

- leader
- follower
- candidate



raft将共识问题分解成两个相对独立的问题，leader election，log replication。流程是先选举出leader，然后leader负责复制、提交log（log中包含command）

为了在任何异常情况下系统不出错，即满足safety属性，对leader election，log replication两个子问题有诸多约束

leader election约束：

- 同一任期内最多只能投一票，先来先得
- 选举人必须比自己知道的更多（比较term，log index）

log replication约束：

- 一个log被复制到大多数节点，就是committed，保证不会回滚
- leader一定包含最新的committed log，因此leader只会追加日志，不会删除覆盖日志
- 不同节点，某个位置上日志相同，那么这个位置之前的所有日志一定是相同的
- Raft never commits log entries from previous terms by counting replicas.



ref:https://www.cnblogs.com/xybaby/p/10124083.html