#### a、b两台服务器怎么判断是否能连通，为什么能用ping和traceroute

ICMP（Internet Control Message Protocol）Internet控制[报文](https://baike.baidu.com/item/报文/3164352)协议。它是[TCP/IP协议簇](https://baike.baidu.com/item/TCP%2FIP协议簇)的一个子协议，用于在IP[主机](https://baike.baidu.com/item/主机/455151)、[路由](https://baike.baidu.com/item/路由)器之间传递控制消息。控制消息是指[网络通](https://baike.baidu.com/item/网络通)不通、[主机](https://baike.baidu.com/item/主机/455151)是否可达、[路由](https://baike.baidu.com/item/路由/363497)是否可用等网络本身的消息。这些控制消息虽然并不传输用户数据，但是对于用户数据的传递起着重要的作用。

ping用了icmp的传递控制信息特性，源主机和目的主机通过这种方式测试网络是否畅通。

traceroute则同时用了icmp的多种特性，在协议中，如果目的主机不可达，则路由器会返回给源主机一个icmp报文，通过这种特性，traceroute不断增加TTL，使得每一次icmp都能返回一个路径上路由器。不用设计新的协议，而能找到源主机到目的主机的路径，感觉是一种比较trick的做法

