HTTP 1.0:
- 无状态（可用cookie/session），无连接（每次都要建立TCP链接）
- 缺点：无法复用链接，阻塞

HTTP 1.1
- 长链接、pipelining
- 缺点：header大，没有优先级控制，阻塞，服务器只能被动响应

HTTP 2.0
- 基于HTTPS，更高安全性；header压缩；二进制frame；可处理优先级；多路复用；服务器可提前主动发送信息乳JS、CSS静态资源，减少等待
