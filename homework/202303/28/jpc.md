# SYN报文什么情况会被丢弃
* TCP 半连接队列和全连接队列满了，造成 SYN 报文被丢弃
* 开启 tcp_tw_recycle 参数，并且在 NAT 环境下，造成 SYN 报文被丢弃
