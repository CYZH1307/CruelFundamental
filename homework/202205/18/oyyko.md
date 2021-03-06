## 什么是粘包，TCP粘包是怎么产生的？怎么解决拆包和粘包？

#### **什么是粘包、拆包？**

TCP本来就是基于字节流而不是消息包的协议. 因此把stream流式数据再打包回去是程序员的责任而不是TCP的责任。

例如我发送两个十进制整数123和345

收到一个TCP包123345，那么它可能被理解为12，3345    1，23345    123，345等 所以程序员把这种现象称为粘包

由于tcp是面向流的协议，不会按照应用开发者的期望保持send输入数据的边界，导致接收侧有可能一下子收到多个应用层报文，需要应用开发者自己分开，有些人觉得这样不合理（那你为啥不用udp），起了个名叫“粘包”。

如果一次请求发送的数据量比较小，没达到缓冲区大小，TCP则会将多个请求合并为同一个请求进行发送，这就形成了粘包问题。

如果一次请求发送的数据量比较大，超过了缓冲区大小，TCP就会将其拆分为多次发送，这就是拆包。

用户数据被tcp发出去的时候，存在多个小尺寸数据被封装在一个tcp报文中发出去的可能性。这种“粘”不是接收侧的效果，而是由于Nagle算法（或者TCP_CORK）的存在，在发送的时候，就把应用开发者多次send的数据，“粘”在一个tcp报文里面发出去了，于是，先被send的数据可能需要等待一段时间，才能跟后面被send的数据一起组成报文发出去

这两个其实都不是“问题”。

第一个是tcp的应有之义，人家本身就是个面向流的协议，如果你要用它传输数据报（datagram），必然要自己实现stream2datagram的过程。这不叫解决问题，这叫实现功能。

第二个是tcp在实现的时候，为了解决大量小报文场景下包头比负载大，导致传输性价比太低的问题，专门设计的。其实在99%的情况下，Nagle算法根本就不会导致可感知的传输延迟，只是在某些场景下，Nagle算法和延迟ACK机制碰到一起，才会导致可感知的延迟。

解决：

靠设计一个**带包头的应用层报文结构**就能解决。包头定长，以特定标志开头，里带着负载长度，这样接收侧只要以定长尝试读取包头，再按照包头里的负载长度读取负载就行了，多出来的数据都留在缓冲区里即可。

其他方法：

**固定包长度**

**以指定字符（串）为包的结束标志**

