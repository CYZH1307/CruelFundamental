### 什么是lamport logical clock?

#### lamport logical clock作用

多个结点之间进行交互，需要实现顺序的一致性，因此逻辑时钟（lamport logical clock）用于区分分区事件的发生顺序的时间机制。

#### lamport logical clock原理

分区之间进行交互的时候确保不同事件之间的顺序

```
1、每个事件对应一个Lamport时间戳，初始值为0
2、如果事件在结点内发生，本地进程时间戳+1
3、如果事件属于发送事件，本地进程的时间戳+1 并且在消息中带上该时间戳
4、如果事件属于接收事件，本地进程中的时间戳=Max(本地时间戳, 消息时间戳) + 1
```

ref:https://blog.xiaohansong.com/lamport-logic-clock.html