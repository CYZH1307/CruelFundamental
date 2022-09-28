### DROP TRUNCATE 和 delete的区别

delete 和 truncate 仅仅删除表数据，drop 连表数据和表结构一起删除

delete 是 DML 语句，操作完以后如果没有不想提交事务还可以回滚，truncate 和 drop 是 DDL 语句，操作完马上生效，不能回滚
