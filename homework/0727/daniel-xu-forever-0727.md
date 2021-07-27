# MySQL 主从原理

主从复制为 读写分离, 高可用 HA 提供了可能.

传统 Mysql 的主从复制主要通过 binlog 进行. binlog 有 stmt, row 和 mixed 三种方式. 写在主上执行, binlog 更新后, 只读节点可以通过同步 binlog 然后重放以实现同步变更.

当代 share everything 的云原生数据库开始采用 redo log 来同步变更, 代表例子为 阿里云 PolarDB
