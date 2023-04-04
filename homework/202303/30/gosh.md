tcptwreuse为什么默认是关闭的？

为什么要TIME_WAIT状态：
防止历史连接中的数据被后面相同四元组的连接错误接收
保证被动关闭连接的一方能被正确的关闭

tcp_tw_reuse:
让客户端快速复用处于TIME_WAIT状态的端口，相当于跳过了TIME_WAIT状态，这样可能会出现两个问题：
	历史RST报文可能会终止后面相同四元组的连接，因为PAWS检查到即使RST是过期的，也不会丢弃。
	如果第四次挥手的ACK报文丢失了，有可能被动关闭连接的一方不能被正常的关闭