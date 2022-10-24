## ping 能访问到一个端口是否支持tcp连接吗，udp呢？

一般来说ping命令使用ICMP协议，而ICMP协议并不指定端口

对于某些没有运行ECHO服务的主机只能使用UDP协议去ping

即结论为：不一定支持tcp连接，支持udp