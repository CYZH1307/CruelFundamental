### 讲一下建立一个HTTP的过程

A向B 建立一个HTTP

（1）域名解析：DNS将域名转换为IP地址

（2）TCP三次握手建立TCP连接：

​		A --[ SYN=1, ACK=0, seq=x] --> B

​		A <--[SYN=1, ACK=1, seq=y, ack=x+1]-- B

​	    A --[ACK=1, seq=x+1,ack=y+1]--> B

（3）开始发送HTTP请求



HTTP1.0  短连接 每次传输的时候都要建立TCP连接

HTTP1.1 支持长连接 只要建立连接后 没有明确提出断开 可以保持TCP连接。

