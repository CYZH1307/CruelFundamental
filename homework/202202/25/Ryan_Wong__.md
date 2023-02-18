# a、b两台服务器怎么判断是否能连通，为什么能用ping和traceroute

## ping原理：

用来检测网络是否畅通或网络连接速度：

在本机输入“ping 10.94.137.109”后，本机构建一个ICMP请求，交给IP层，计算目标地址和本机是否在同一网段。

1.本机和目标机在同一网段：

IP层协议在本机ARP缓存表

- 先试图寻找10.94.137.109和其MAC地址的映射关系，以得到目标MAC。
- 找不到映射关系则在网段内发ARP请求广播，得到目标MAC。

2.本机和目标机不在同一网段：

IP层协议走到路由，在路由的ARP缓存表

- 先试图寻找10.94.137.109的路由和其路由MAC地址的映射关系，以得到目标路由MAC。
- 找不到映射关系则在路由层级内发ARP请求广播，得到目标MAC。



找到目标MAC之后便走到数据链路层构建数据帧，传给目标机。目标机收到后，从相同过程构建应答数据帧到本机。本机收到后即为ping通。



## traceroute原理：

traceroute是为了弥补ping不能记录经过路由器的缺陷（IP首部字段只能放有限个IP地址）而设计的。通过不断发生存时间不断+1的udp包，让路径上每个路由器都返回包含自己IP的数据帧到本机，这样就可以记录所有路由信息了。C++风格伪代码如下：



```c++
vector<int> route;

int curTTL = 1; // 初始生存时间

RESPONSE resp; // 每次udp的响应

while(resp.IP != targetIP) {

	UDP p; // 每次循环发一个新包

	p.TTL = curTTL;

	resp = ping(p，targetIP);

	route.push_back(resp.IP);

	curTTL++; // 每次循环发出新包的生存时间++

}

return route; // 包含了路径上所有IP
```

