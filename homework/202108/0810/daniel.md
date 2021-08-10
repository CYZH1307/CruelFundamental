# 消息队列如何避免重复消费


由于网络的不确定性, 可能 consumer 消费过后, offset并没有提交, 导致会有重复消息从 producer 发送过来, 此时要做的是保证 业务处理 message 的幂等性, 既多次消费 同样的message 产生相同的效果. 

* 数据库 unique key + 全局 messages id 