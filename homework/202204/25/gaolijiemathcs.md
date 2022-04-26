### 有没有了解过paxos和zookeeper的zab算法，它们之间有啥区别

相同点：

- 两者系统都有Leader角色，负责协调多个Follower进程运行
- Leader等待过半数Follower投票反馈后，才会提交提案
- ZAB中提案包括一个epoch值，代表当前Leader周期，Paxos中也有。

区别：

- 设计目标不同
  - Paxos用于构建分布式一致性状态机系统
  - ZAB用于构建高可用分布式数据主备系统
- 流程不同
  - Paxos两阶段完成新Leader选举
    - 读阶段：新主进程通过和其他进程通信，收集上一个主进程的提案并且提交。
    - 写阶段：主进程Leader开始提出自己的提案
  - ZAB三阶段完成：
    - 发现阶段：等同Paxos读阶段
    - 同步阶段：新Leader确保过半Follower已经提交，前Leader周期的所有事务提案。
    - 广播阶段：等同Paxos写阶段

ZAB同步阶段有助于保证Leader在新周期提出事务提案之前，所有进程都已经完成了对之前事务的提交。