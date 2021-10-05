# Delete in 为什么不用索引
- https://cloud.tencent.com/developer/article/1884307
- https://blog.csdn.net/tiantiandjava/article/details/80064612

## 过程
- select * from users where name in (select name from employees);
- show warnings;
- select * from users join employees;  # 使用索引
- delete from users where name in (select name from employees);
- 会全表扫描
- 原因： 创建了临时表，没有索引
- 解决办法：使用join
- 临时内存表 tmp_table_size 
- 物理内存表  max_heap_table_size
- TODO: LooseScan, semi join
