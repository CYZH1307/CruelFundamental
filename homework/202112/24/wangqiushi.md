传输层：负责主机中两个进程的通信，即端到端的通信。传输单位是报文段或用户数据段。功能一：可靠传输，不可靠传输  功能二：差错控制  功能三：流量控制  功能四：复用（多个应用层进程可同时使用下面运输层的服务）分用（运输层把收到的信息分别交付给上面应用层中相应的进程） 主要协议：TCP、UDP

网络层：把分组从源端传到目的端，为分组交换网上的不同主机提供通信服务。网络层传输单位是数据报。功能一：路由选择 功能二：流量控制 功能三：差错控制 功能四：拥塞控制 主要协议：IP、IPX、ICMP、IGMP、ARP、RARP、OSPF
