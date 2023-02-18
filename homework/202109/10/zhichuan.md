# close wait

ref: https://blog.csdn.net/jayxujia123/article/details/113814686?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-4.no_search_link&spm=1001.2101.3001.4242

## close wait

收到FIN之后，server就处于close wait状态，然后发送未完成的数据包后再发FIN

如果有大量的close wait，说明server的socket一直没有close，说明大量的资源被占用，且无法被释放，或者没有主动close释放连接

## last ack

last_ack收不到ack的时候，会重发送FIN

tcp_orphan_retries 内核参数设置的次数，就会放弃重传，然后进入close状态。 tcp_orphan_retries 内核参数的默认值是 0 ｜ 8（如果有keep alive）， 200ms

tcp_keepalive_intvl:探测消息发送的频率
tcp_keepalive_probes:TCP发送keepalive探测以确定该连接已经断开的次数
tcp_keepalive_time:当keepalive打开的情况下，TCP发送keepalive消息的频率

sysctl -a|grep tcp_keepalive

net.ipv4.tcp_keepalive_intvl = 30
net.ipv4.tcp_keepalive_probes = 2
net.ipv4.tcp_keepalive_time = 160