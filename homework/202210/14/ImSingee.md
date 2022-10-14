
> [原文](https://notes.singee.me/#/page/%E4%BB%80%E4%B9%88%E6%98%AF%20time_wait%20%E7%8A%B6%E6%80%81%EF%BC%8C%E4%B8%BA%E4%BB%80%E4%B9%88%E9%9C%80%E8%A6%81%20time_wait%20%E7%8A%B6%E6%80%81%EF%BC%9F%E6%97%B6%E9%97%B4%E6%98%AF%E5%A4%9A%E4%B9%85%EF%BC%8C%E4%B8%BA%E4%BB%80%E4%B9%88%EF%BC%9F)

- ![Replaced by Image Uploader](https://vip2.loli.io/2022/08/08/aw5coTdSxhbDQCs.png)  
-  
- [[TCP]] 中，在终止连接时（**任何一端均可主动终止连接**）会发送 FIN，同时对端在接收到此 FIN 且自己的消息也发送完后向发起终止方发送 FIN，发起终止方接收到对端发送的 FIN 后进入 TIME_WAIT 状态  
-  
  > TIME_WAIT 状态不属于客户端也不属于服务端，它属于的是要求终止的一端（首先发送 FIN 的）  

- TIME_WAIT 的主要作用在于防止 ACK 丢失时 SEQ 被复用导致异常 ，需等待 2 * [[MSL]]  
-  
-  
  > 在网上各种文章中，对于 TIME_WAIT 的解释似乎都只有「防止 ACK 丢失」，但没有说丢失了到底会怎么样  
    
  我当时的反应：丢了就丢了呗，对方重发 FIN 发现被 RST 了直接断了不也一样吗？为什么非得挂着等浪费资源？  
    
  事实上，本质而言，TIME_WAIT 是防止端口和 SEQ 被复用导致异常  

-  
- ## 异常  
	- ### 发起相同连接导致异常  
		- 该问题通常出现于客户端主动断开连接  
		- 如果在 TIME_WAIT 阶段中，ACK 丢失了，那么 **这条连接在发起方认为已经是没有的了而在对端认为仍然存活**  
		- 如果在 ACK 丢失后立刻向对端同端口发送请求是有概率得到与前述正在关闭中的连接相同的端口和 SEQ 的，这时如果发送 SYN 会导致对方错误地认为是旧连接而 RST  
	- ### 包阻塞延迟导致异常  
		- 如果在前置连接中，有包延迟了但是没有丢失，且一直延迟到前一连接已经关闭了而新的连接恰好复用了前置连接的端口和 SEQ，就会导致数据错乱  
- ## 等待时间  
	- 2 * MSL  
		- MSL 在 RFC 中定义为 120s，而在 Linux 中被硬编码为 60s  
		- 即目前 Linux 下 TIME_WAIT 时间是固定的 120s  
		- 这一时间无法修改，但可以通过 [[linger]] 强制不使用 TIME_WAIT 而是使用 RST，或配置 tcp_tw_reuse 允许内核重用 TIME_WAIT 状态的连接（**但都不推荐，尽量还是加机器解决**）  
	- 这一时长是为了保证关闭时仍然在网络中传输的包一定已经被丢了  
		- 1MSL 不够是因为要考虑的是一个来回的时间  
-  
- ## 参考  
	- [RFC 793: Transmission Control Protocol](https://www.rfc-editor.org/rfc/rfc793.html#section-3.5)  
	- [network protocols - Setting TIME_WAIT TCP - Stack Overflow](https://stackoverflow.com/questions/337115/setting-time-wait-tcp)  
	- [为什么 TCP 协议有 TIME_WAIT 状态 - 面向信仰编程](https://draveness.me/whys-the-design-tcp-time-wait/#fn:5)  

