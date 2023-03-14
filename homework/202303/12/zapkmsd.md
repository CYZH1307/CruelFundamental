为什么需要四次挥手？

- TCP连接的断开需要四次挥手的原因是因为在TCP连接的建立过程中，客户端和服务器需要通过三次握手建立连接，其中在第二次握手时，客户端发送的确认报文（ACK）和请求报文（SYN）是合并在一起发送的。而在连接断开时，由于TCP连接是全双工的，双方都可以向对方发送数据，因此无法合并数据的传输和连接的断开过程。