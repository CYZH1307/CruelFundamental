DROP、TRUNCATE 和 DELETE 命令有什么区别？
Drop
删除表数据和表结构, 如索引, 触发器等都被删除. Drop属于DDL(数据定义语言), 不支持where删除条件.

Truncate
删除表数据, 不删除表结构. 删除后自增ID将重置. Truncate属于DDL(数据定义语言), 不支持where删除条件.

Delete
删除表中的行, 不删除表结构. 删除后自增ID不重置. Delete属于DML(数据操作语言), 支持where删除条件.
