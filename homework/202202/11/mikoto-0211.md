## 如何用Redis实现一个消息队列？
对于Redis2.0版本之前，常使用List类型或者ZSet类型实现消息队列，其中使用List类型实现的和普通队列实现方式比较类似，易于理解，通过lpush、rpop等操作实现先进先出

利用List实现的消息队列的消息不支持重复消费、没有按照主题订阅的功能、不支持消费消息确认等。

ZSet实现方式和List类似，但由于ZSet多了一个分值属性，因此可以实现更多的功能，比如用来存储时间戳，以此实现延迟消息队列。

Redis2.0之后，Redis新怎了专门的发布和订阅的类型，Publisher（发布者）和Subscriber（订阅者）来实现消息队列。

该模式允许生产者只生产一次消息，由中间件负责将消息复制到多个消息队列，每个消息队列由对应的消费组消费。

优点：
- 典型广播模式，一个消息可以发送到多个消费者
- 多信道订阅，消费者可以同时订阅多个信道，从而接收多类消息
- 消息即时发送，无需等待消费者读取，消费者会自动接收到信道发布的消息

缺点：
- 消息一旦发布，则后续不能接收，也就是说如果消息发布时若客户端不在线，则消息丢失
- 无法保证每个消费者接收的时间一致
- 若消费者客户端出现消息积压，到一定程度就会被强制断开，导致消息意外丢失，通常发生在消息生产速度远大于消费速度时。

总结： Pub/Sub模式不适合做消息存储，消息积压类的业务，而擅长处理广播，即时通讯，及时反馈的业务。

Redis5.0之后新增了Stream类型，可以利用其xadd和xrange实现消息的存入和读取，并且提供了xack手动确认消息消费的命令，可以用其实现消费者确认的功能。
Stream中有一个消息链表，将所有加入的消息都穿起来，每个消息都有一个唯一的ID和对应的内容。消息是持久化的，Redis重启后内容还在。
每个Stream中都可以挂多个消费组，同一消费组还可以挂多个消费者。
