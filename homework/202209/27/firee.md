### DROP、TRUNCATE 和 DELETE 命令有什么区别？
1.truncate
删除表中的内容，不删除表结构，释放空间；

2.delete
删除内容，不删除表结构，但不释放空间,只能作用于table

2.drop
drop语句将表所占用的空间全释放掉,可以作用于table和view
