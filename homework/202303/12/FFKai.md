## 为什么需要四次挥手？

四次挥手（four-way handshake）是TCP协议关闭连接的过程，它的目的是让双方都能安全地关闭连接，并确保双方都接收到了对方的关闭请求。TCP协议采用四次挥手是因为TCP是一种全双工协议，双方都可以向对方发送数据。在关闭连接时，双方需要分别发送关闭请求和关闭确认，共计四次握手。

下面是四次挥手的具体过程：

1. 客户端发送FIN报文：客户端发送FIN报文，表示客户端已经没有数据要发送了。
2. 服务端发送ACK报文：服务端接收到客户端的FIN报文后，发送ACK报文确认接收到了客户端的关闭请求。
3. 服务端发送FIN报文：服务端发送FIN报文，表示服务端已经没有数据要发送了。
4. 客户端发送ACK报文：客户端接收到服务端的FIN报文后，发送ACK报文确认接收到了服务端的关闭请求。

通过四次挥手，TCP连接可以安全地关闭，避免了数据的丢失和混乱。需要注意的是，在关闭连接时，双方都可以向对方发送数据，因此在四次挥手过程中需要确保双方都接收到了对方的关闭请求和关闭确认。如果出现一个方向发送了关闭请求，但对方没有响应的情况，会导致连接处于半关闭状态，可能会引发一些问题，因此四次挥手是必要的。