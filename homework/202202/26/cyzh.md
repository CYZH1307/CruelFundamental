## 2022/02/26

### traceroute是怎么traceback最终让发出traceroute的客户端得到信息的

#### Tracetoute原理

Traceroute程序设计是利用ICMP即IP header的TTL(time to live) 的栏位field，首先，traceroute会发送一个TTL为1的datagram，当到达下一个路由器后，TTL变为了0，路由器就会将这个datagram丢掉，并返回一个【TTL time exceeded】的消息，traceroute接到消息后就知道了路由器的地址，接着发送一个TTL为 2的包