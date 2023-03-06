## 03月06日
### HTTP版本区别
- HTTP/0.9：仅支持GET
- HTTP/1.0：支持POST等新特性，引入Header
- HTTP/1.1：支持长链接，管线化
- HTTP/2.0：引入二进制协议，头部压缩(HPACK)，服务器推送，并发传输(Stream)
- HTTP/3.0：使用新的QUIC(无队头阻塞，更快建立连接，连接迁移)，默认TLS1.3加密