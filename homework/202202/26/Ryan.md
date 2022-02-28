## traceroute是怎么让发出它的客户端得到信息的：

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
