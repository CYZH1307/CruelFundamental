## 五种常见的 Redis 数据类型是怎么实现？
- string: SDS（API安全，获取长度O(1)，二进制存储）缓存对象，常规计数，分布式锁，共享session对象
list: 双向链表或ziplist（quicklist）密集存储，消息队列（无全局ID，消费组）
set: 哈希表或整数集合，聚合计算，点赞，共同关注，抽奖活动
zset: ziplist（quicklist）或 跳表（不是严格的RMQ）排序场景，排行榜，电话，姓名等
hash: ziplist（quicklist）或 哈希表，缓存对象，比如购物车
bitmap: 二进制，二进制状态的统计，比如签到，判断用户登陆，连续登陆天数
stream: 消息队列，支持全局唯一ID，消费组
GEO: 经纬度，地理位置，最近有哪些
HyperLogLog: 求和平均数，第一个1的位置，大数统计，百万网页UV计数