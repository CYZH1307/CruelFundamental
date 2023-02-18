# 消息队列中，如何保证消息的顺序性？

1. 应用场景

   mysql系统的binlog同步功能，把上亿级的同步数据从一个mysql库原封不动的同步到另一个mysql库。其中在mysql里增删改查一条数据，对应出来了增删改3条binlog日志，接着将这三条binlog发送到MQ里面，再消费出来依次执行，这三条binlog日志必须得按照顺序执行，不然会导致结果错误

2. 解决方案

​		在RabbitMQ里拆分多个queue，每个queue一个consumer，然后consumer内部用内存队列做排列，分发给底层不同的worker来处理

