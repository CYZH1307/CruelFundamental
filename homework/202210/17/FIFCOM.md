# 为什么http协议要设计成无连接的

无连接: 虽然http使用了tcp连接，但通信双方在交换http报文之前不需要先建立http连接。

而且http工作在应用层，底层基于tcp或者udp都可以，不需要特别关注底层细节

#### 但是

[MDN](https://developer.mozilla.org/en-US/search?q=connectionless)中并没有提及HTTP是无连接的，[这里](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Basics_of_HTTP)也没说，只说HTTP是无状态的。
