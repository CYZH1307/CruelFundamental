## ZooKeepershi
Zookeeper是用paxos算法实现的，可以保证每个节点数据的强一致性
1. 系统节点分为主节点和从节点，主节点可以进信给操作，及监控从节点。
2. 从节点可以进行读操作和转发写操作，从节点的follower可以参与投票选leader，而observer不可以。
3. leader发送的广播是原子操作，要么完成，要么报错。
4. leader的操作顺序保证由写操作的唯一id来保证
