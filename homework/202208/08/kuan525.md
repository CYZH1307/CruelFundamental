### 什么是 TIME_WAIT 状态，为什么需要 TIME_WAIT 状态？时间是多久，为什么？
1. 优雅的关闭TCP连接，也就是尽量保证被动关闭的一端收到它自己发出去的FIN报文的ACK确认报文；
2. 处理延迟的重复报文，这主要是为了避免前后两个使用相同四元组的连接中的前一个连接的报文干扰后一个连接。

> 时间：2MSL

如果只考虑上述第一个目标，则TIME_WAIT状态需要持续的时间应该参考对端的RTO（重传超时时间）以及MSL（报文在网络中的最大生存时间）来计算而不是仅仅按MSL来计算，因为只要对端没有收到针对FIN报文的ACK，就会一直持续重传FIN报文直到重传超时，所以最能实现完美关闭连接的时长计算方式应该是从对端发送第一个FIN报文开始计时到它最后一次重传FIN报文这段时长加上MSL，但这个计算方式过于保守，只有在所有的ACK报文都丢失的情况下才需要这么长的时间；另外，第一个目标虽然重要，但并不十分关键，因为既然已经到了关闭连接的最后一步，说明在这个TCP连接上的所有用户数据已经完成可靠传输，所以要不要完美的关闭这个连接其实已经不是那么关键了。因此，（我猜）RFC标准的制定者才决定以网络丢包不太严重为前提条件，然后根据第二个目标来计算TIME_WAIT状态应该持续的时长。

再来看一下《UNIX网络编程》在描述为什么需要TIME_WAIT状态时的一段话：

> Since the duration of the TIME_WAIT state is twice the MSL, this allows MSL seconds for packet in one direction to be lost, and another MSL seconds for the reply to be lost. By enforcing this rule, we are guaranteed that when we successfully establish a TCP connecton, all old duplicates from previous incarnations of the connection have expired in the network.

这段文字说明了TIME_WAIT状态持续2MSL的时间可以让一个TCP连接的两端发出的报文都从网络中消失，从而保证下一个使用了相同四元组的tcp连接不会被上一个连接的报文所干扰。

