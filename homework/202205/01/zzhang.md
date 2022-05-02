## 如何处理消息丢失问题？

以Kafka为例

1. 生产者弄丢
- 生产者基本不会弄丢消息，因为生产者发送消息会等待 Kafka 响应成功，如果响应失败，生产者会自动不断地重试。

2. Kafaka弄丢
- Kafka 通常会一台 leader + 两台 follower，当生产者消息刚写入 leader 成功，但是还没同步到 follower 时，leader 宕机了，此时会重新选举 leader，新的 leader 由于还未同步到这条数据，导致该条消息丢失。
- 解决办法是做一些配置，当有其他 follower 同步到了消息后才通知生产者消息接收成功了。配置如下：
给 topic 设置 replication.factor 参数：这个值必须大于 1，要求每个 partition 必须有至少 2 个copy；
要求一个 leader 至少感知到有至少一个 follower 还跟自己保持联系，没掉队，这样才能确保 leader 挂了还有一个follower；
要求每条数据，必须是写入所有 replica 之后，才能认为是写成功了。

3. 消费者弄丢
- 关闭 offset 的自动提交
