# 半连接队列

reference: https://www.cnblogs.com/xiaolincoding/p/12995358.html



TCP三次握手时，内核维护两个队列

- 半连接队列，即SYN队列
- 全连接队列，即accepet队列



- 服务端收到客户端第一次握手的SYN后，内核会把该连接存到SYN队列，并给客户端发SYN+ACK

- 客户端返回ACK，服务端收到第三次握手的ACK后，内核把该连接从SYN队列移除，然后创建新的完全连接添加到accept队列。

  

