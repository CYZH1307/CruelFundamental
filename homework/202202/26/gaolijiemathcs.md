###  traceroute是怎么traceback最终让发出traceroute的客户端得到信息的

（1）源地址A发出ICMP请求回显(ICMP Echo Request)数据包到达目的地址，TTL=1

（2）到达路由器，TTL减1

（3）TTL=0包丢弃，路由器向源地址发送一个ICMP超时通知（ICMP Time Exceeded Message），内容包含IP源地址，IP包的所有内容与路由器的IP地址。

（4）源地址收到该ICMP，包显示路由信息。

（5）重复（1）~（5）每次设置TTL增加1

（6）直到目标地址收到探测包，并且返回ICMP恢复。

（7）当源地址收到ICMP Echo Reply 停止trace。