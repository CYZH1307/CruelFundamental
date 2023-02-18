# Message Queue 对比分析

### ActiveMQ, RabbitMQ, RocketMQ, Kafka

Reference: https://www.zhihu.com/question/275090117, http://blog.zollty.com/b/archive/high-throughput-high-availability-MQ-comparative-analysis.html, https://blog.csdn.net/wqc19920906/article/details/82193316, https://www.infoq.cn/article/kafka-vs-rabbitmq

#### 简介

ActiveMQ:  https://activemq.apache.org/

- 使用Java完全支持JMS1.1和J2EE 1.4规范的 JMS Provider实现，少量代码就可以高效地实现高级应用场景。
- 可插拔的传输协议支持，比如：in-VM, TCP, SSL, NIO, UDP, multicast, JGroups and JXTA transports

RabbitMQ: https://www.rabbitmq.com/

- 在吞吐量方面略有逊色，但支持更多的消息队列功能。
- 支持很多的协议：AMQP，XMPP, SMTP,STOMP

RocketMQ: https://rocketmq.apache.org/

- from 阿里，在设计时参考了 Kafka，被广泛应用在订单，交易，充值，流计算，消息推送，日志流式处理，binglog分发等场景
- 具备高吞吐量、高可用性、适合大规模分布式系统应用等特点

Kafka: https://kafka.apache.org/, https://www.educative.io/courses/grokking-adv-system-design-intvw/

- 使用scala实现的一个高性能分布式Publish/Subscribe消息队列系统，吞吐量相对更高，适用于海量数据收集与传递场景，例如日志采集和集中分析。
- 目前越来越多的开源分布式处理系统如 Cloudera、Apache Storm、Spark、Flink 等都支持与 Kafka 集成。

|            | ActiveMQ       | RabbitMQ                                    | RocketMQ                             | Kafka                    |
| ---------- | -------------- | ------------------------------------------- | ------------------------------------ | ------------------------ |
| 单机吞吐量 | 万级           | 万级                                        | 万级                                 | 十万级                   |
| 消息延迟   |                | 微秒级                                      | 毫秒级                               | 毫秒级                   |
| 语言       | Jave           | Erlang                                      | Java                                 | Scala/Java               |
| 订阅形式   | p2p, broadcast | direct, topic, headers, fan-out (broadcast) | Topic/message-tag based subscription | Topic based subscription |
| 持久化     | 支持少量堆积   | 支持少量堆积                                | 支持大量堆积                         | 支持大量堆积             |
| 顺序消息   | no             | no                                          | yes                                  | yes                      |
| 消息回溯   | no             | no                                          | 指定时间点的回溯                     | 指定分区offset位置的回溯 |
| 负载均衡   | 可以           | 可以                                        | 支持较好                             | 支持很好                 |
| 可用性     | 高，主从       | 高，主从                                    | 非常高，分布式                       | 非常高，分布式           |



#### 其他：Redis

Reference: https://blog.csdn.net/wqc19920906/article/details/82193316

- 一个Key-Value的NoSQL数据库，支持MQ功能，可以当做一个轻量级的队列服务来使用。
- 性能对比：RabbitMQ vs Redis
  - 各执行100万次入队出队，每10万次记录一次执行时间
  - 测试数据分为128Bytes、512Bytes、1K和10K四个不同大小的数据。
  - 入队时，当数据比较小时Redis的性能要高于RabbitMQ，而如果数据大小超过了10K，Redis则慢的无法忍受
  - 出队时，无论数据大小，Redis都表现出非常好的性能，而RabbitMQ的出队性能则远低于Redis。



---

### 相关问题

Reference: https://segmentfault.com/a/1190000019123489



#### 为什么使用消息队列？

Analysis:

其实就是问问你消息队列都有哪些使用场景，然后你项目里具体是什么场景，说说你在这个场景里用消息队列是什么？其实场景有很多，比较核心的有 3 个：解耦、异步、削峰。

Ans:

解耦：

一开始A系统需要发送数据到BCD三个系统，通过接口调用发送。后来E系统要用这个数据，C系统不需要了。A系统要时刻考虑BCDE四个系统如果挂了怎么办。

使用了MQ，A系统产生数据，发送给MQ，需要数据的系统去MQ消费。如果新系统需要数据，可以从MQ里消费，如果不需要这条数据，取消对MQ消息的消费。A就不需要考虑给谁发送消息，不用维护这些代码，也不用考虑别的系统是否调用成功、失败超时等情况。通过MQ的Pub/Sub消息模型，A系统与其他系统解耦。



异步：

A 系统接收一个请求，需要在自己本地写库，还需要在 BCD 三个系统写库，自己本地写库要 3ms，BCD 三个系统分别写库要 300ms、450ms、200ms。最终请求总延时是 3 + 300 + 450 + 200 = 953ms，接近 1s。一般对于用户直接的操作要求是每个请求都必须在 200 ms 以内完成。

使用了 MQ，A 系统连续发送 3 条消息到 MQ 队列中，假如耗时 5ms，A 系统从接受一个请求到返回响应给用户，总时长是 3 + 5 = 8ms



削峰：

每天0:00-12:00，每秒并发请求数量只有50个。但是到12:00-13:00，每秒并发请求数量暴增到5k+条。但系统基于MySQL，最多只能支持每秒2k个请求。每秒请求到5k会导致MySQL崩溃，用户无法再使用系统。但是高峰期一过，到下午又成了低峰期，每秒请求数下降至每秒50个，对系统几乎没有任何的压力。



使用了 MQ，高峰期每秒 5k 个请求写入 MQ，系统每秒钟最多处理 2k 个请求（基于MySQL 每秒钟最多处理 2k 个来算）。系统从 MQ 中慢慢拉取请求，每秒钟就拉取 2k 个请求，不超过自己每秒能处理的最大请求数量，哪怕是高峰期的时候，系统也绝对不会挂掉。而 MQ 每秒钟 5k 个请求进来，2k 个请求出去，导致在中午高峰期（1 个小时），可能有几十万甚至几百万的请求积压在 MQ 中。这个短暂的高峰期积压是 ok 的，因为高峰期过了之后，每秒钟就 50 个请求进 MQ，但是系统依然会按照每秒 2k 个请求的速度在处理。所以说，只要高峰期一过，系统就会快速将积压的消息给解决掉。



#### 消息队列有什么优点和缺点？

优点：

同上，在特殊场景下有解耦、异步、削峰的作用。



缺点：

1. 系统可用性降低：系统引入的外部依赖越多，越容易挂掉。
2. 系统复杂度提高：需要保证消息没有重复消费，处理消息丢失情况，保证消息传递的顺序性等。
3. 一致性问题：A处理完返回成功了，但在BCD三个系统里，BD成功，C未成功，导致数据不一致。





#### Kafka、ActiveMQ、RabbitMQ、RocketMQ 都有什么区别，以及适合哪些场景？

Ans:

（区别略）

最早大家都用ActiveMQ，但现在用的不多，没经过大规模吞吐量场景的验证，社区也不活跃。

后来大家开始用RabbitMQ，但Erlang劝退Java工程师，但开源，活跃度高。中小型公司，技术实力较为一般，技术挑战不是特别高，用 RabbitMQ 是不错的选择。

越来越多公司使用RocketMQ，阿里出品，捐给了Apache。大型公司，基础架构研发实力较强，用 RocketMQ 是很好的选择。

如果是大数据领域的实时计算、日志采集等场景，用 Kafka 是业内标准的。



*RabbitMQ和Kafka对比：https://www.infoq.cn/article/kafka-vs-rabbitmq

