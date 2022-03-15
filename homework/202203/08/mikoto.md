## 请你说说MySQL引擎和区别

MySQL有两种储存引擎InnoDB和MyISAM

其中二者的区别为：
- InnoDB支持事务，可以进行Commit和Rollback
- MyISAM只支持表级锁，InnoDB支持行级锁，提高并发性能
- InnoDB支持外键
- MyISAM崩溃后随还概率高于InnoDB，且恢复速度也更慢
- MyISAM支持压缩表和空间数据索引，InnoDB需要更多内存和存储
- InnoDB支持在线热备份

应用场景

- MyISAM用于管理非事务表，提供高速存储和检索（MyISAM强调性能，查询具有原子性，执行速度高于InnoDB）以及全文搜索能力，如果表比较小或者是只读数据（有大量SELECT），可以使用MyISAM
- InnoDB支持事务，并发情况下性能好，基本可以代替MyISAM
