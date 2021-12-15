# 提高系统并发性

Reference: https://www.nowcoder.com/questionTerminal/c8abcd6d7f0843d3bf78f0f19acabb47，https://blog.csdn.net/qq_27577503/article/details/106312621



Cluster：

1. 增多服务器
2. 增加带宽（网卡、switch）



单机：

1. 多线程、多核编程，消除CPU瓶颈
2. 采用poll，利用状态监测和通知方式，消除网络I/O阻塞
3. 多用事件驱动、异步消息机制，减少不必要的等待
4. 能Pipeline的pipeline做



业务处理：

1. 优化数据库性能：SQL优化、索引优化 etc
2. 采用缓存Redis 或 Memcached