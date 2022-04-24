### 讲一讲raft算法的基本流程？raft算法里面如果出现split brain怎么办
#### 单个 Candidate 的竞选
有三种节点：Follower、Candidate 和 Leader。Leader 会周期性的发送心跳包给 Follower。每个 Follower 都设置了一个随机的竞选超时时间，一般为 150ms~300ms，如果在这个时间内没有收到 Leader 的心跳包，就会变成 Candidate，进入竞选阶段。


此时 Node A 发送投票请求给其它所有节点。


其它节点会对请求进行回复，如果超过一半的节点回复了，那么该 Candidate 就会变成 Leader。


之后 Leader 会周期性地发送心跳包给 Follower，Follower 接收到心跳包，会重新开始计时。


#### 多个 Candidate 竞选
如果有多个 Follower 成为 Candidate，并且所获得票数相同，那么就需要重新开始投票。例如下图中 Node B 和 Node D 都获得两票，需要重新开始投票。


由于每个节点设置的随机竞选超时时间不同，因此下一次再次出现多个 Candidate 并获得同样票数的概率很低。


数据同步
来自客户端的修改都会被传入 Leader。注意该修改还未被提交，只是写入日志中。


Leader 会把修改复制到所有 Follower。


Leader 会等待大多数的 Follower 也进行了修改，然后才将修改提交。


此时 Leader 会通知的所有 Follower 让它们也提交修改，此时所有节点的值达成一致。
