### a、b两台服务器怎么判断是否能连通，为什么能用ping和traceroute

ping、telnet、traceroute



ping：向目的主机发送ICMP请求报文。看本地主机能不能与另外一台主机交换数据包。

telnet：可以用于看目的端口能不能访问。用TCP协议

traceroute：也是用ICMP协议，用于定位计算机和目标计算机之间的路由器。

