### 简述Traceroute的原理？

功能：Traceroute(linux)/tracert(win) 网络调试工具，用于显示数据包在IP网络中经过的路由器的IP地址。

原理：Traceroute程序会先向目标主机发出TTL为1的数据包，发送数据包的计算机与目标主机之间的路径中的第一个路由器，在转发数据包的时候逐步将TTL减1，TTL被减为了0，于是向最初的数据包发送一个ICMP TTL数据包，Traceroute得到了目标主机之间的路径上的第一个路由器的IP地址，然后依次发送TTL为2、3、4...的数据包，探测出来与目标主机之间路径上每一个路由器的IP地址。

ref:https://www.cnblogs.com/zyd112/p/7196341.html