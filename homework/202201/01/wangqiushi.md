TCP建立连接之前，服务端状态从closed变为listen状态时候，会创建两个队列，分别是半连接队列（syns queue）和全连接队列（accept queue）。客户端向服务端发送SYN，服务端接收到信息后，向客户端发送返回SYN和ACK，状态变为SYNreceived状态。这个连接会放到半连接队列里面。等客户端返回ACK之后，服务端接受到信息之后，三次握手完成。这个连接会放在全连接队列里。