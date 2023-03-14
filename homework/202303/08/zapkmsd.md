为什么是三次握手？不是两次、四次？

如果只有两次会发生以下问题：

- 客户端发送的SYN报文可能在网络中丢失，导致服务器无法确认请求，从而无法建立连接。

- 服务器发送的SYN+ACK报文也可能在网络中丢失，导致客户端无法确认连接建立，从而无法进行通信。

四次握手会造成不必要的资源浪费