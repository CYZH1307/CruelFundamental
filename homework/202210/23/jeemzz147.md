### ping 能访问到一个端口是否支持tcp连接吗，udp呢？
ping 命令是基于 ICMP 协议来工作的，Internet Control Message Protocol。ping 命令会发送一份ICMP回显请求报文给目标主机，并等待目标主机返回ICMP回显应答。因为ICMP协议会要求目标主机在收到消息之后，必须返回ICMP应答消息给源主机，如果源主机在一定时间内收到了目标主机的应答，则表明两台主机之间网络是可达的。
