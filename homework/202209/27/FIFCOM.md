## DROP、TRUNCATE 和 DELETE 命令有什么区别？

```sql
# 删除表
drop table table_name;
```
```sql
# 清空表
truncate table table_name;
```
```sql
# 删除数据
# where 里的是条件，如果没有条件则删除所有内容
delete from table_name where column = value;
```