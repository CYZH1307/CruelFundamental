# mysql如何保证主从数据一致

Mysql主从使用的是binlog日志传输方式，完成从库对主库的复制，

但是主库宕机，binlog还未发送，从库直接切换为主库则会导致主备不一致等问题。


Mysql后来引入了GTID模式

主库和从库分别维护一个GTID的集合，标识所有该数据库实例执行过的事务，从库可以把自己的GTID集合发送给主库，主库检查和自己GTID集合的差集，该差集就是主库需要发送给从库的数据
