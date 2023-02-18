如何用Redis实现一个消息队列？

1. List 队列

   - 用List数据类型
   - 生产者使用 `LPUSH` 发布消息

   ```
   $> LPUSH queue msg1
   (integer) 1
   $> LPUSH queue msg2
   (integer) 2
   ```

   - 消费者用 `RPOP` 消耗数据

   ```
   $> RPOP queue
   "msg1"
   $> RPOP queue
   "msg2"
   $> RPOP queue
   (nil)
   ```

   - Redis 还提供了「阻塞式」拉取消息的命令：BRPOP / BLPOP：
     - 如果队列为空，消费者在拉取消息时就阻塞等待，有新消息过来时就通知消费者立即处理新消息

2. Pub/Sub

   - 多个消费者、多个发布者
   - Redis 提供了 PUBLISH / SUBSCRIBE 命令，来完成发布、订阅的操作。

   - Pub/Sub 提供匹配订阅模式，允许消费者根据一定规则，订阅多个自己感兴趣的队列。

   ```
   $> SUBSCRIBE queue.* 
   ```

   ```
   $> PUBLISH queue.p1 msg1
   (integer) 1
   $> PUBLISH queue.p2 msg2
   (integer) 2
   ```

3. Stream

   - Stream 通过 XADD 和 XREAD 完成最简单的生产、消费模型

   ```
   $> XADD queue * name aabbcc // 表示让Redis自动生成消息ID
   "16183841301-0"
   $> XADD queue * name ddeeff 
   "16182141394-0"
   ```

   ```
   $> XREAD COUNT 5 STREAMS queue 0-0
   1) 1) "queue"
      2) 1) 1) "16183841301-0"
      			 2) 1) "name"
      			 		2) "aabbcc"
         2) 1) "16182141394-0"
         	 2) 1) "name"
         	 		2) "ddeeff"
   ```

   - 创建消费者组： `XGROUP`
   - 在指定消费组下，消费者读取信息：`XREADGROUP`
   - 可重新消费（用消息ID）



用Redis做消息队列的缺点：

- 存在数据丢失的可能
- 数据都存储在内存中，消息堆积对内存压力大

