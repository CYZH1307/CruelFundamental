# 什么是lamport logical clock?

## 什么是 logical clock?

逻辑时钟是用来区分物理时钟提出来的概念，用来区分分布式系统中事件发生的相对顺序。

Clock condition:
对于任意事件 a, b  如果 a 先于 b 发生，那么 C(a) < C(b)， 反之不亦然，因为有可能是并发事件。

- 如果 a, b 都在一个本地进程 p_i 里，且 a 先于 b 发生，那么 C_i(a) < C_i(b)
- 如果 a 是进程 p_i 里某消息的发送事件，b 是 p_j 里该消息的接受事件，那么 C_i(a) < C_j(b)

1. 每个事件对应一个Lamport时间戳，初始值为0
2. 如果事件在节点内发生，本地进程中的时间戳加1
3. 如果事件属于发送事件，本地进程中的时间戳加1并在消息中带上该时间戳
4. 如果事件属于接收事件，本地进程中的时间戳 = Max(本地时间戳，消息中的时间戳) + 1

Lamport 时钟只能保证因果关系（偏序）的正确性，不能保证绝对时序的正确性。