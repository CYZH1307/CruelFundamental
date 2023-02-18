### 数据库主从复制的原理是什么？

主库日志传给从库，从库日志重放。具体流程如下：



主库A和从库B维持一个长连接。

1.从库B通过change master指令，设置主库A的ip、用户名、密码等等，以及从哪个位置开始请求binlog（文件名+日志偏移量）

2.从库B执行start slave 启动两个线程，io_thread和sql_thread，io_thread负责与主库A建立连接，sql_thread负责对sql日志从库重放。

3.主库A校验完用户名、密码等，开始按照备库B传来的位置，从本地读取binlog，发给B。

4.备库B通过io_thread从主库的dump_thread拿到binlog，写到本地，得到中转日志(relay log)。

5.sql_thread读取中转日志，解析出日志命令，执行。

