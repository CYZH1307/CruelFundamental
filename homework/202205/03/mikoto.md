## 消息队列如何保证消息的顺序性？

#### 理解为什么会出现顺序错乱？
一个queue，有多个consumer去消费，虽然说consumer从消息队列中取出消息时候是有顺序的，但是无法保证consumer执行的顺序。
或者说一个queue对应一个consumer，但是consumer中出现了多线程消费


#### 解决办法：
对于第一个问题，可以拆分多个queue，每个queue对应一个consumer，如此一来可以保证单个consumer消费消息的顺序性。

或者说采用一个queue但是每个消息有对应consumer，取出消息后在consumer内部用内存队列排队，这个感觉本质上差不多。

多线程的话，比较麻烦，感觉不是很有必要。
