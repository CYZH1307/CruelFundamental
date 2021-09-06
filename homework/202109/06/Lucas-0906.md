# RocketMQ 消息消费
- https://mp.weixin.qq.com/s/vxHzNs23-P4BvYvyME6_nQ

## 事件驱动
- 建立长链接，由事件驱动来事实发送消息
- 如果主动推送，消费速度慢，就会造成消费端消息堆积
- 消费端主动拉取，更为方便
- RacketMQ 虽然有Push类，但不是真正的推送
- 底层实际采用长轮询机制，即拉取的方式

## 消费方式
- 消费模型由Consumer决定，消费维度为Topic
- 集群消费，一条消息只能由同一个Group里面的一个Consumer消费
- 多个Group的时候，每个Group里都有一个消费者能获取到消息
- 广播消费，一个Group里面每个Consumer都会消费一遍

## 获取方式
- 消费者首次请求Broker，判断Broker是否有消息
- 如果有，响应消费者，等待下一个消费者的请求
- 如果没有，运行DefaultMessageStore::ReputMessageService::run
- PullRequestHoldService来保持链接，每五秒检查pullRequestTable有没有消息
- 每个一毫秒检查commitLog是否有新消息，有的话写入pullRequestTable
- 使用消费者的位移
