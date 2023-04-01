不是
TCP Keep Alive 是 TCP 的一种自动保活机制，防止长时间的空闲连接实际上已经在一方被异常断开了
而 HTTP Keep-Alive 是 HTTP 的一个机制，用于允许在同一条 TCP 连接中发送多个请求响应对，默认情况下发送一次请求响应就会断开 TCP 连接，HTTP Keep-Alive 使得同一条 TCP 连接得以复用
