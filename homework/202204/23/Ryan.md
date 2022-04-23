# 讲一讲raft算法的基本流程？raft算法里面如果出现split brain怎么办

### 流程

任何节点启动时都是follower状态，在一段时间内如果没有收到来自leader的心跳，从follower切换到candidate，任期更新，发起选举。  
如果收到集群中大多数的票（含自己的一票）则切换到新任期的leader状态；如果发现其他节点比自己先成为了新任期的leader，则主动切换到follower。  
如果长时间平票，则根据随机的超时时间（每个节点不同）来取消节点的新任期选举资格。

### 脑裂处理

raft的投票机制保证了每个任期之内只有一个leader，只有当网络出现故障，分割成若干partitions的情况下，才会出现每个partition各有一个leader的状况。但有且只有一个能和majority通信，且必然任期最新。   
当网络合并时，新的leader必然是任期最小的那个。别的leaders会退化成followers并同步集群数据。
