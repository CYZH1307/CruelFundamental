### 什么是粘包，TCP粘包是怎么产生的？怎么解决拆包和粘包？

#### 粘包 TCP粘包如何产生

占包，可能是多个报文合并一起发送，"粘"在一起了。tcp是面向流的协议，不会按照开发者期望按照数据边界发送数据，可能接收方一次会收到多个应用层报文，开发者需要自行分开。tcp能够将多个小尺寸数据封装在一个tcp报文中发送。因为发送的过程取决于发送窗口、拥塞窗口，当前发送缓冲区等等情况，一次发送TCP报文可能会有多个消息体。

#### 怎么解决拆包和粘包

- 设计一个带每个报文"包"头的应用层报文结构，包头固定，特定标志开头，带着每个包的长度。接收方从报文当中分析包头与包长度，按长度读取。
- 每次只发一个，避免多个包粘贴在一起发送，但是可能导致网络拥塞。
- 固定长度消息：灵活度低。
- 加包头包尾。

加标志位 需要在报文内出现 特定字符加转义字符，否则会误判。
