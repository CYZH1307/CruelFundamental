Q: RocketMQ为什么要主动拉取消息而不使用事件监听方式？

A: push方式可能会导致consumer端积压,低负载consumer不能及时拿到任务.pull方式可以更好低根据各consumer自身负载合理分配任务

RocketMQ由NameServer,Producer,Broker,Consumer四部分组成
NameServer: 无状态动态列表
Producer: 发消息到Broker
Broker: MQ,负责收发及持久化消息
Consumer: 从Broker来取消息消费,消费完进行ack
