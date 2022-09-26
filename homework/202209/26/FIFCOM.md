## 触发器和存储过程的区别是什么？

触发器是一种与增删改有关的数据库对象。触发器定义了一系列操作，这一系列操作称为触发程序。当触发器所在表上出现INSERT、UPDATE以及DETETE操作时，将立即激活触发器，即表的操作事件触发表上的触发程序的执行。

触发器可以维护冗余数据，实现外键级联选项等

触发器不能调用将数据返回客户端的存储过程，也不能使用采用CALL语句的动态SQL

触发器不能使用以显式或隐式方式开始或结束事务的语句，如START TRANSACTION、COMMIT或ROLLBACK

创建触发器：
```sql
create trigger trigger_name
    [after | before] -- 在操作前或后
    [insert | update | delete] -- 操作类型
    on table_name -- 触发器所在表
    for each row -- 行级触发器
    [when condition]
    begin
        sql statement; -- 触发程序
    end;
```

```sql
-- 回收站触发器
delimiter //
create trigger `my_trigger` 
    after delete on `my_table`
    for each row
    begin
        insert into `recycle_table` (`id`, `name`) values (old.id, old.name);
    end;
    // 
delimiter ;
```

存储过程是一组预先编译好的sql语句的集合，可以被app作为一个整体来调用，可以接收参数，可以返回参数,说白了就是函数

独立放置且拥有不同功能的语句块称之为PROCEDURE（过程）（存储过程）

存储过程的语法：
```sql
delimiter //
create procedure pro_name1 (in param_name varchar(10))
    -- [in / out / inout] param_name type (in是传入参数， out是传出参数)
    select * from course where cname = param_name;
    //
delimiter ;
call pro_name1('text');
set @custom_param = 'text2';
call pro_name1(@custom_param);
```