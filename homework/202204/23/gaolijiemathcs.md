### 讲一讲raft算法的基本流程？raft算法里面如果出现split brain怎么办

#### raft算法的基本流程

##### 作用：

raft算法可以使得多个结点对同一个事情达成一致看法。例如客户端发起一组指令，能够确保这组服务器都是以同样的顺序收到指令，产生的效果一致。

##### 身份分类：

- Leader：负责接收客户端的请求，通过日志复制给其他服务器，告知其他服务器如何操作。
- Candidate：候选人用于选举Leader的中间态。
- Follower：被动响应，响应Candidate和Leader。

##### Term：

任期。如果选举成功，以选举成功为起点，在当前Term内由leader管理。如果选举失败，则会开启新的Term重新选举。

##### RPC方式：

- RequestVote RPC，由candidate发送，要求其他节点选举其为leader的RPC
- AppendEntries RPC，由leader发送，向follower发送心跳或者同步日志。
- InstallSnapshot RPC，服务器之间传递镜像快照使用。

##### 选举流程：

Raft通过心跳机制触发leader选举，服务器启动时为follower，follower从leader和candidate收到有效rpc会保持follower状态。如果follower没有收到有效消息时间超过election timeout，则会假设没有可用leader，触发选举leader过程。

1.follower增加当前term变成candidate

2.candidate投票给自己，并且发送RequestVote RPC给集群中的其他服务器。

3.收到RequestVote的服务器，同一term中只会按照先到先得投票给至多一个candidate。且只会投票给log至少和自己一样多的candidate。

4.candidate会保持状态2直到下面之一发生：

（1）自己当选，收到大部分结点的投票，变成leader。

（2）另外一个服务器成为了leader，收到leader合法心跳包。则边回follower

（3）一段时间还是没有胜者。开启新一轮选举。

##### 日志复制与执行

选举结束，leader响应客户端请求。

1.leader将客户端请求作为一条新的条目写入日志

2.leader发送AppendEntries RPC给其他服务器去备份日志条目。

3.follower收到leader的AppendEntries RPCs，将该条目记录到日志并返回回应给leader。

4.当该条日志被安全备份即leader收到了半数以上的服务器回应该条记录已经被记录，则认为是有效的。leader将该条目由state machine处理，并且执行结果返回给客户端，leader结点在下次心跳等待AppendEntries RPCs时，记录提交的日志条目编号commitIndex

5.follower在收到leader的AppendEntries RPCs，其中会包含commitIndex，follower判断该条目是否已经执行，未执行则执行commitIndex以及之前的相应条目。

##### 日志压缩

正常运行过程中，raft集群日志增长很快，通常使用镜像快照压缩日志，通过当前的state写入到snapshot中，该点的日志就可以丢弃。

- Raft集群中每个结点都会自主产生snapshot
- snapshot包括当前state machine状态、last include index(snapshot中包含的最后一条日志条目的索引)，last included term(snapshot中包含的最后一条日志条目所属的term)、集群成员信息。
- 当leader发现要发送给follower日志条目已经在snapshot中，会发送installSnapshot RPCs给follower，这种情况通常发生在集群有新结点加入的时候。
- follower收到InstallSnapshot RPC的时候将snapshot写入到本地，并且根据snapshot内容重新设置state machine，如果本地有更老的snapshot或者日志，则可以丢弃。



#### Raft算法解决脑裂问题

如果系统同时选举出多于1个的leader就发生了脑裂情况。

raft中保证了一个任期至多只会出现一个leader：

- 一个任期内，一个follower结点最多只能投一票，且先到先得。
- candidate存储的日志至少要和follower一样新
- 只有获得了超过半数的投票才有机会成为leader。



ref:

https://zhuanlan.zhihu.com/p/383555591

https://www.jianshu.com/p/37877e046132