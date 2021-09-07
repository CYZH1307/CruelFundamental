# RocketMQ

## 组成

Nameserver	无状态，
Producer	消息生产者，负责发消息到Broker。
Broker	就是MQ本身，负责收发消息、持久化消息等。
Consumer	消息消费者，负责从Broker上拉取消息进行消费，消费完进行ack。

## 为什么不push ？？？

Rocket 没有push，push模式底层也是长轮询

pull的话，consumer根据自己的情况自主选择pull broker端的资源

感觉这个题目本身有点问题，
mq的producer consumer模式本身就不会push
哪里有强制消费者消费的模式
