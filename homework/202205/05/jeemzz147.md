#### 如何处理消息队列的消息积压问题？

consumer消费的速度落后于消息生产的速度，可以扩容消费者群组。

优化消费端业务逻辑，扩容消费端

采用临时的消费端处理积压的消息队列，处理完毕后恢复
