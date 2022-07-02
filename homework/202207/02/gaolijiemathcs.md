### 什么是ARP协议？在IPV6中被什么协议替代？

#### ARP协议

ARP（Address Resolution Protocol），是一个通过解析网络层地址(ip地址)寻找数据链路层地址(mac地址)的传输协议，就是ip地址和mac地址的映射。主要在ipv4中用。ARP协议属于网络层(IP层)。

#### 在IPV6中被什么协议替代？

ipv6中被邻居发现协议(NDP)代替。

NDP实际上替换了IPv4中的ARP和ICMP路由发现协议。

NDP定义使用ICMPv6报文实现地址解析，跟踪邻居状态，重复地址检测，路由发现以及重定向等功能。



ref:https://cshihong.github.io/2018/01/29/IPv6%E9%82%BB%E5%B1%85%E5%8F%91%E7%8E%B0%E5%8D%8F%E8%AE%AE/