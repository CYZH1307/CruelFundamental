MySQL引擎比较
1.MyISAM空间和内存使用较低，执行读取操作速度快。不支持事务处理，表损坏后不能恢复数据。（查询要求高）

2.InnoDB支持事物处理和外来键，并发控制，但比ISAM和MyISAM引擎慢很多。（事物处理，并发控制，频繁更新删除操作）

3.MEMORY：数据存储在内存。数据处理速度快。服务器要有足够的内存维持MEMORY存储引擎的表的使用，适用较小数据库表。内存出现异常会影响数据，重启或关机，则数据都会消失。生命周期短，一次性。安全性低。（查询的临时表）
