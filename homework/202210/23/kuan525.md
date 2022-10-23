### ping命令用tcp还是udp

ping命令用tcp还是udp
万次阅读
2019-12-26 16:14:45
ping应该是属于osi七层模型中的应用层。
ping命令使用的tcp报文还是udp报文呢？答：ping命令使用的是ICMP报文，ICMP报文封装在ip包里。ICMP协议也是tcp/ip协议族中的一个子协议，所以从这一层来看，icmp报文和tcp报文，udp报文是同一个级别。所以ping命令使用的报文既不是tcp报文也不是udp报文。

但是ICMP 跟TCP和UDP没有归属关系，ICMP位于传输层之下，属网络层。用的IP报头。
TCP/UDP 在第四层：传输层
IP/ICMP 在第三层：网络层