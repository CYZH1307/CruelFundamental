### MySQL是怎么保证主从一致的

主从一致，当主库修改数据以后，从库会通过binlog进行主从同步。

GTID模式为全局事务ID，一个事务提交以后生成事务的唯一标识。可以用于主备切换。

例如：主备关系X和Y，主库为X，都开启了GTID，主备切换流程：

```
1.X执行stop slave
2.Y执行DDL语句，不需要关闭Binlog。
3.执行完成以后，查出DDL语句GTID，记为server_uuid_ofY:gno
4.实例X执行语句（让Y更新所有的binlog记录，确保不会在实例X执行这条更新）
	set GTID_NEXT="server_uuid_of_Y:gno";
	begin;
	commit;
	set gtid_next=automatic;
	start slave;
5.执行主备切换
```



GTID，一主多从切换方便。