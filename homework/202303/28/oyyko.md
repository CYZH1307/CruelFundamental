SYN 报文被丢弃的两种场景：

开启 tcp_tw_recycle 参数，并且在 NAT 环境下，造成 SYN 报文被丢弃

TCP 两个队列满了（半连接队列和全连接队列），造成 SYN 报文被丢弃