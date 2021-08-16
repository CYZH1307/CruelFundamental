# Redis 分布式锁原理
- https://juejin.cn/post/6844903717641142285
- https://segmentfault.com/a/1190000014128432
- https://xie.infoq.cn/article/556aaceb68789b9de4807f1c2
- https://www.cnblogs.com/rgcLOVEyaya/p/RGC_LOVE_YAYA_1003days.html

## 要求
- 互斥
- 释放死锁
- 容错性

## 问题
- 客户端 A 连接 Redis 主节点 X，并获取锁
- 主节点X尚未将数据复制到从节点Y
- 主节点 X 此时宕机
- 从节点 Y 被选举为主节点
- 客户端 B 连接新的 Redis 主节点 Y，获取到锁
- 以上情景无法实现互斥

## 解决办法
- 给锁设置超时
- 超过时间，锁自动释放？
- 超时时间，主节点被迫降级？
- 超过时间，从节点才能被选举为主节点

## 单实例锁
- set KEY VALUE NX PX 30000
- NX = No Existing 时才设置
- PX = Expired Time， 30,000 毫秒

## 集群锁
- 锁获取时间为1秒，超时为5秒
- 一秒内没有获得锁，则放弃，下次再尝试获取
- 获得成功后，减去获取锁的时间，则剩下锁的使用时间


