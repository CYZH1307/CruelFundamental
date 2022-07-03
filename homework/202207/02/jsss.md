# 什么是ARP协议？在IPV6中被什么协议替代？


## **ARP**协议是地址解析协议, 负责将IP地址映射成MAC物理地址. 

ARP请求协议以广播的形式在局域网内传播, 响应以单播的形式答复. 当在统一网段内通信时, MAC地址就是真正的MAC地址; 当在不同的网段通信时, 无法找到目标IP的真实MAC地址, 因此先填成网关的MAC地址, 当网关转发该包时, 再将目的MAC地址填成目标MAC地址.

## IPV6中使用NDP协议替代ARP协议.

邻居发现协议NDP（Neighbor Discovery Protocol）是IPv6协议体系中一个重要的基础协议。邻居发现协议替代了IPv4的ARP（Address Resolution Protocol）和ICMP路由器发现（Router Discovery），它定义了使用ICMPv6报文实现地址解析，跟踪邻居状态，重复地址检测，路由器发现以及重定向等功能

ref Link: https://zhuanlan.zhihu.com/p/450688487
