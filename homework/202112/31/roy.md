# 简述TCP四次挥手过程


# 为什么TCP挥手需要4次？ #

客户端与服务端想要通信，需要双方共同确认能够给对方发信息；确认的流程分为两步：分别是发送方发送请求通信的消息，接收方回复收到请求的信息。所以双发共同确认一般来讲总共会产生4条信息。TCP建立连接时只需要
3次，是因为服务端回复客户端请求时，可以把自己请求通信的要求一起发送，这样就节省了一条消息。然而在终止通信时，服务端却不能这样做，因为在他回复客户端终止通信的请求时，不能立即发送自己不会再发送信息的通知
。这是因为在服务端可能还有未完成的请求，服务端需要把他们都处理完毕，才会发送种植通信的请求。

# 为什么四次挥手释放连接时需要等待2MSL？ #

如果客户端立即关闭链接的话，则无法处理服务端没有收到第四次挥手（确认终止通信的消息）的情况。如果服务端没有收到确认信息的话，则会重新发送请求，客户端需要维持链接来处理这种情况。
