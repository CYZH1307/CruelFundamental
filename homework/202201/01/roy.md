# 简述半连接队列

半连接队列是在第一次握手之后、服务端用来储存还未完全建立连接的队列。因为端口建立最大的tcp数量有上限。
第三次握手之后，若服务端全连接队列未满，服务端会把尽可能多的半连接队列里的链接放入全连接队列。
若全连接队列满了，tcp_abort_on_overflow会决定如何处理半链接队里的链接：0重新发送握手；1放弃链接。
