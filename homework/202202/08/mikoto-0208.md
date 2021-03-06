# Redis部署方式
1. 主从模式
将数据库分为主数据库和从数据库，其中主数据库一般负责写，而从数据库一般负责读，实现读写分离

原理：
- 主数据库正常配置
- 在从数据库中配置跟随的主数据库
- 主数据库收到sync指令后，通过RDB快照当前数据库，结束后发送给从服务器
- 所有的写操作都通过主数据库，然后通过异步方式向从数据库推送增量数据
- 大部分的读操作都通过从数据库
- 主数据库的写入操作和与从数据库的同步是异步进行

优点：可以合理搭配主从数据库的数量将负载有效的分担到不同配置的服务器中。
缺点：由于数据库读写是在不同的数据库中异步实现，因此存在数据不同步的可能性

2. 哨兵模式
对于一主多从的数据库模式，如果主数据库崩溃，则数据库服务中断。有了哨兵模式后，若主数据库崩溃，则可以通过哨兵监控，将从服务器转化为主服务器，哨兵之间也能相互监控。
Redis-sentinel本身是一个独立运行的进程，能够监控多个master-slave集群，发现master宕机后能够进行切换


3. 集群模式
随业务量和数据量的增加，redis性能到达单节点瓶颈，垂直扩容（提升各节点自身的性能，如增加节点的储存空间）受到机器性能限制，而水平扩容（增加节点数量）涉及到对应用的影响以及数据迁移中的丢失风险。
集群模式正是可以解决这些问题，用于实现负载均衡，集群模式主要可以解决分片问题，将整个数据按照规则分布在不同的子节点上，每个节点负责自己部分的数据。
