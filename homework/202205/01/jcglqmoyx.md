[reference](https://juejin.cn/post/7022530252857409550)

生产者将消息传入消息队列的时候， 要有一个确认机制，保证消息队列接收到消息后返回通知，告知生产者MQ已经收到消息。

消息队列将队列中的消息进行持久化，保证即使MQ服务器宕机，也不会丢失消息。

消费者读取消息队列中的消息时，要将每个请求处理完毕才通知消息队列，不要读取到消息后马上给MQ服务返回通知。