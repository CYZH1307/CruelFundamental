### 你根据什么来决定你的项目用sql还是nosql database?
* SQL易理解，操作方便，由于事务的ACID特性，可以维护数据的一致性，常用的mysql，Oracle比较稳定，很少宕机
* 但SQL在高并发下IO压力大，由于二级索引的存在，维护的代价大，并且由于ACID特性，需要加锁和隔离界别，使得读写性能变差，因此如果需要应对高并发IO场景，频繁的扩展数据时用NOSQL
