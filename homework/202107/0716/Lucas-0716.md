# 分布系统的设计，分布式系统CAP，分布式系统的模型

## CAP 理论
- Consistency，强一致性，分布式系统里多个数据副本数据一致
- Availability，可用性，系统在任何时候都可用
- Partition Tolerance，分区容错性，系统不受网络分区影响
- 只能三选二
- CA，Oracle，MySQL
- CP，基本没有
- AP，弱一致性，DynamoDB，etcd

## ACID
- Atomicity，要不成功，要不失败，不能成功一半
- Consistency，逻辑一致性
- Isolation，多个事务发生时，互相不影响
- Durability，事务运行成功后，更新是永久的
