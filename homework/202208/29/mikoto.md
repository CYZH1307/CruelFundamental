## s什么是raft算法？

raft是一个共识算法，即多个节点对同一个事情达成一致的三发，即使在部分节点鼓掌，网络延时或者网络分割的算法。

raft会先选举leader，leader完全负责replicated log的管理，leader负责接受所有客户端的更新请求，然后复制到follower节点，并在安全的时候执行这些请求，如果leader故障，follower会选举出新的leader。

raft协议中，任一节点时刻处于以下三种状态之一：
1. leader
2. follower
3. candidate


节点启动时都是follower状态，如果一段时间没有收到来自leader的心跳，就从follower切换称为candidate，发起选举，如果收到majority的票，就切换到leader状态，如果发现其它节点比自己更新，就主动切换成follower状态。

系统中最多有一个leader，如果一段时间内没有leader，就会通过投票选举leader，leader会持续给follower发送心跳消息，表明自己的存活状态。



