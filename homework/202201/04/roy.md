# HTTP 协议

Hyper Text Transfer Protocol

HTTP是应用层协议，HTTp/1， HTTP/2 是基于TCP， HTTP/3 则是基于UDP。

advantages：
- stateless：可以轻松scale up服务，通过cookie，可以把保存状态的压力转移到用户端。
- connectionless： 在web1.0时代，用户与web交互的频率比较低，短链接更节省资源。现在keep alive特性可建立长连接。
- media independent：什么媒体文件都可以传 

disadvantages：
- insecure：明文传输，需要TLS加密。


