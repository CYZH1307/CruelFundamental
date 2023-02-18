### 说说TCP 3次握手的过程和四次挥手的过程。

TCP3次握手

```
client === Server
(1) client ====SYN=1, seq=x===> server
(2) client <===SYN=1, ACK=1, seq=y, ack=x+1=== server
(3) client ====ACK=1, seq=x+1, ack=y+1===> server
第三次握手已经可以携带通信报文。
```

TCP4次挥手

```
client === Server
(1) client ====FIN=1, seq=u===> server
(2) client <===ACK=1, seq=v, ack=u+1 === server  
		client断开连接 但server端可以继续发消息
(3) client <===FIN=1, ACK=1, seq=p, ack=u+1 === server 
		server确认断开
(4) client ====ACK=1, seq=u+1, ack=p+1 ===> server
		client等待TIME-WAI=2MSL 确认没有server发报文回来 四次挥手断开。
```

