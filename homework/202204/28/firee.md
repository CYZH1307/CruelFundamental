### 数据库主从复制的原理是什么？

通过log记录下数据库的操作

通过主从之间传输log，在从库重新跑log来达到和主库一样的状态