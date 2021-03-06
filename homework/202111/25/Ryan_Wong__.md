# MQ对比 #

ActiveMQ：

    1.优点：功能完备，技术成熟度高。
    2.缺点：高吞吐下容易丢失数据，不支持回溯，社区热度不高。
    3.应用：用于传统行业中需要对消息进行解耦和异步处理的场景。

RabbitMQ：

    1.优点：在简单集群环境下延时极低，功能完备，界面做得好，社区活跃
    2.缺点：吞吐量低，不支持负载均衡，集群扩展开销大，大型集群环境下性能不好。
    3.应用：多用于互联网公司。

RocketMQ：

    1.优点：并发性能好，吞吐量大，集群扩展开销低，支持复杂业务场景，可用性高
    2.缺点：接口是自己定义的一套，系统迁移困难
    3.应用：多用于大规模吞吐、复杂业务场景中。

Kafka：

    1.优点：吞吐量极大，在复杂集群环境下延时低，可用性高，集群扩展开销低。
    2.缺点：有可能出现消息重复消费。
    3.应用：业界标准
