# ZooKeeper 原理 #
ZK是最流行的开源分布式管理项目之一，其优点在于各节点的数据强一致性，原理如下：  
1.**区分主从**：ZK服务集群中，server分为主节点leader和从节点learner（进一步分为follower和observer）。
    i.leader不直接接收客户端请求，负责更新系统状态，保证每个learner数据一致。
    ii.每个follower都可独立接受读写请求，本地处理读、写和leader同步，也可以参加投票选出新的leader。
    iii.每个observer都本地处理读，写转发到leader，不参与投票。这个的设计初衷是增加读性能。
2.**原子广播**：
    i.leader对learner的广播是内部不可分的，要么成功要么失败。
    ii.为保证写竞争有序，所有learner的提案都有一个全局唯一的事务标识zkID。
    iii.通知回调机制：每个learner都有一个单独的异步队列接收leader的信息，learner通过异步调用该队列来进一步防止阻塞，提升性能。
