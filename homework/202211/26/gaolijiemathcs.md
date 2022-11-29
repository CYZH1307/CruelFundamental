### bin log和redo log的区别

- redo log wei InnoDB引擎特有的；binlog是MySQL的Server层实现的，所有引擎都能使用。
- redo log为物理日志，记录为某个数据页上做了什么修改；binlog为逻辑日志，记录这个语句的原始逻辑，例如给"ID=2这一行c字段+1"
- redo log循环写，空间固定可能会用完；binlog为追加写入，追加写为binlog写到一定大小会切换到下一个，不会覆盖以前的日志。