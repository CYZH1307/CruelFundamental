# 如何处理消息队列的消息积压问题

### 1.避免出现积压

主要问题出现在消费端，当消费端的性能低下、消费能力赶不上生产能力时，就会出现积压。可以考虑采用扩容消费者群组的方式。

### 2.处理已经出现的积压

如果是由于业务上生产量大幅提升（如双十一抢购）导致的消息积压，可以：1、通过扩容消费端的实例数来提升总体的消费能力；2、关闭一些不重要的业务，减少发送方发送的数据量，最低限度让系统还能正常运转。  

如果是生产端没有大的变化，而消费变慢，则考虑是否出现重复失败消费、死锁等动作，或者是否消费端资源耗尽。