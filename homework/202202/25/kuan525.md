#### a、b两台服务器怎么判断是否能连通，为什么能用ping和traceroute

##### ping： 

ping使用的是ICMP协议，他发送ICMP回送请求消息给目的主机。ICMP协议规定：目的主机必须返回ICMP回送应答消息给源主机，如果源主机在一定时间内收到应答，表明主机可达。ICMP协议是通过IP协议发送的，IP协议是无连接的，不可靠的数据报协议。

##### traceroute：

Traceroute是用来侦测由源主机到目的主机所经过的路由的情况的重要工具，也是最简洁的工具，尽管ping可以进行侦测，但是ping受到IP头的限制（IP首部字段最多只能放9个IP地址），ping不能完全记录所经过的路由器，所以才会引入Traceroute。

Traceroute原理：其实Traceroute的原理很简单，他收到目的主机IP后，首先给目的主机发送一个TTL=1（TTL指生存时间）的udp数据包，而经过的第一个路由器收到这个数据包之后，自动把TTL减去1，而TTL变为0之后，路由器就将这个数据包抛弃了，并同时产生一个主机不可达的ICMP超时数据报给主机。主机收到这个ICMP数据报以后，会发送一个TTL=2的数据报给目的主机，然后刺激第二个路由器给主机发送ICMP数据报，如此反复，直到到达目的主机。这样Traceroute就可以拿到所有路由器的IP，从而避开IP头只能记录有限路由的IP地址。



1. 从源地址发出一个UDP探测包到目的地址，并将TTL设置为1；

2. 到达路由器时，将TTL减1；

3. 当TTL变为0时，包被丢弃，路由器向源地址发回一个ICMP超时通知（ICMP Time Exceeded Message），内含发送IP包的源地址，IP包的所有内容及路由器的IP地址；

4. 当源地址收到该ICMP包时，显示这一跳路由信息；

5. 重复1～5，并每次设置TTL加1；

6. 直至目标地址收到探测数据包，并返回端口不可达通知（ICMP Port Unreachable）；

7. 当源地址收到ICMP Port Unreachable包时停止traceroute。



那么我们怎么知道UDP到没到达目的主机呢？这就涉及一个技巧的问题，TCP和UDP协议有一个端口号定义，而普通的网络程序只监控少数的几个号码较小的端口，比如说80,比如说23,等等。而traceroute发送的是端口>30000(真变态)的UDP报，所以到达目的主机的时候，目的主机只能发送一个端口不可达的ICMP数据报给主机。主机接到这个报告以后就知道目的主机到了。

参考文章：[segmentfault：David Cao](https://segmentfault.com/a/1190000040633589) 、[魔降风云变](https://www.cnblogs.com/machangwei-8/)