五种旧数据类型：String，Hahs，List，Set，Zset
新加数据结构：BitMap（2.2），HyperLogLog（2.8），GEO（3.2），Stream（5.0）

String：缓存对象，常规计数，分布式锁，共享session
List：消息队列（无全局ID，无消费组形式）等
Hash：缓存对象，购物车（人物ID：商品ID：商品数量）
Set：聚合计算（并，交，差），比如点赞，共同关注，抽奖活动
Zset：排序场景，排行榜，电话，姓名排序等等
BitMap：二进制统计状态，比如签到，判断登陆状态，连续签到总数
HyperLogLog：海量数据统计，但是不完全准确
GEO：存储地理位置（经纬度），可以搜索区域内其他元素
Stream：消息队列，相较于List，增加了全局ID和消费组。但是持久化和主从集群下的数据丢失风险依然存在，可以使用专业的消息队列中间件，比如RabbitMQ 或 Kafka 