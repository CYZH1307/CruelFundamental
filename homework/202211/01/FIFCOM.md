## TCP 是否可以改为两次握手？（请从 ISN 的角度思考）

不可以，TCP是全双工的，如果只有两次握手：
```
A SYN---> B
A <---ACK B
```
则无法实现全双工连接，具体原因如下：

TCP 中双方通过使用序列号来跟踪他们发送的内容。接收方可以使用对方发言者的序列号来确认它所收到的内容。

但序列号不是从0开始的。它从随机数 ISN (初始序列号)开始。由于 TCP 是全双工的，双方都可以“说话”，因此双方都必须随机生成一个 ISN 作为起始序列号。

即双方在“说话”前都需要通知对方ISN：
```
A SYN---> B:SYNchronize with my ISN of X
A <---ACK B:I received your syn, I ACKnowledge that I am ready for X+1
A <---SYN B:SYNchronize with my ISN of Y
A ACK---> B:I received your syn, I ACKnowledge that I am ready for Y+1
```

总结三次握手为：
```
A SYN--->     B
A <---ACK,SYN B
A ACK--->     B
```

而两次握手没有同步双方的ISN，只允许一方建立、另一方同意，也就是说只有一方可以发送数据
```
A SYN---> B:SYNchronize with my ISN of X
A <---ACK B:I received your syn, I ACKnowledge that I am ready for X+1
```
