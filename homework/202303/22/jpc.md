### 在TIME_WAIT状态的TCP连接收到SYN会发生什么？

* 如果SYN是合法的，则会重新使用该连接，并跳过2MSL进入SYN_RECV状态，进行连接过程。

* 如果SYN不合法，则会再回复一个四次挥手的ACK报文，客服端收到后会发现不是所期望的确认号，回复RST报文给服务端。