# ZAB和Paxos算法的联系和区别

#### 			总的来说，ZAB算法和Paxos算法本质区别在于两者设计目标的不同，ZAB主要用于构建一个高可用的分布式数据主备系统，而Paxos算法则是用于构建一个分布式的一致性状态系统

​				

##### 1. 两者联系

- 两者都存在一个类似于Leader的进程角色，由其负责协调多个Follower进程的运行
- Leader进程会等待超过半数的Follower做出正确的反馈，才会将一个提案进行提交
- 在ZAB协议，每个Proposal都包含一个epoch值，用来代表当前Leader周期，在Paxos算法中同样有这样的标识，只是名字变成了Ballot



##### 2.两者区别

- Paxos算法，一个新选举产生主进程会进行两个阶段工作
  - 第一阶段读阶段，这个阶段，新的主进程会通过和所有其他进程进行通信的方式来收集上一个主进程提出的提案，并将他们提交。
  - 第二阶段被称为写阶段，这个阶段，当前主进程开始提出自己的提案。
- ZAB在Paxos的基础上添加了一个同步节点，同步节点之前，ZAB也存在一个和Paxos算法中的读阶段非常类似的过程，称为（Discovery）阶段。在同步阶段中，新的Leader会确保存在过半的Follower已经提交了之前Leader周期中的所有事务Proposal。这一步骤有效的保证Leader在新周期中提出事务Proposal之前，所有进程都已经完成了对之前所有事务Proposal的提交。一旦完成同步后，ZAB就执行和Paxos类似的写阶段
  
  
