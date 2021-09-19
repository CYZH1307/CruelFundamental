## Kafka如何保证消息有序不丢？

### 消息队列 简介
#### Original Definition(in OS)
Message queues is a software-engineering component that is used for **inter-process** communications and **inter-thread** communications. 
It is a queue for messaging, which is mainly about the passing of control or of content. 

#### Newer Definition(Middleware):
It is a form of **asynchronous** service-to-service communication used in **microservices architectures**. 
Messages are stored on the queue until they are processed and deleted. Each message is processed only once, by a single consumer.

![image](https://user-images.githubusercontent.com/90691060/133916493-80fa392d-4901-4ab6-8473-88968323eafd.png)


#### Kafka 简介
Kafka provides the following functionalities:

Publish (write) and subscribe to (read) streams of events.
Store streams of events durably and reliably.
Process streams of events as they occur or retrospectively.


#### 如何保证有序不丢：

生产者如果有一个发送失败了，后面的就不能继续发送（？

消费者需要在执行完真正的业务逻辑之后再返回响应给Broker

同步和异步回调都需要做好`try-catch`，如果Broker返回写入失败等错误消息，需要重试发送。当多次发送失败需要作报警，日志记录等。

如果Broker是集群部署，有多副本机制，即消息不仅仅要写入当前Broker,还需要写入副本机中





