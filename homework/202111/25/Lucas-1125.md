
# 0713 - RabbitMQ, Kafka, RocketMQ, ActiveMQ, 以及其他消息中间件
- https://developer.aliyun.com/article/216084
- https://segmentfault.com/a/1190000019547121
- TODO: https://blog.csdn.net/qq_28959087/article/details/111290204

|          | ActiveMQ  | RabbitMQ | RocketMQ | Kafka    | ZeroMQ  |
|----------|-----------|----------|----------|----------|---------|
| 特点     |           |          |          | 高性能   | 轻量级  |
| JMS规范  |           |          |          | No       |         |
| 协议     | AMQP/REST | AMQP     | Defined  | Defined  | TCP/UDP |
| 语言     | Java      | Erlang   | Java     | Scala    | C       |
| 消息事务 | 支持      |          | 支持     | 支持     | 不支持  |
| 消息有序 | 不支持    | 不支持   | Yes      | 同一分区 | 不支持  |
| 消息拉取 |           |          | Yes      |          |         |
| 消息订阅 |           |          | Yes      |          |         |
| 分布式   | 主从      | 主从     | 分布式   | 分布式   |         |
| 性能     |           |          | 高       | 高       |         |
| 并发     |           | 高       |          |          |         |
| 吞吐量   |           |          | 亿级     | 大       |         |
| 重传     | Yes       | Yes      |          | No?      | Yes     |
| 持久化   | Yes       | Yes      |          |          | No      |
| 应用场景 |           |          |          | 日志     | P2P     |


