#### 如何用Redis实现一个消息队列?

1. 基于List的 LPUSH+BRPOP 的实现

2. PUB/SUB，订阅/发布模式

3. 基于Sorted-Set的实现

4. 基于Stream类型的实现 来实现消息队列