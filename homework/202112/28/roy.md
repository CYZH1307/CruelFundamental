# TCP滑动窗口、拥塞控制

# 滑动窗口 #
由于tcp留等待确认的机制太浪费时间，我们可以选择不用同步等待，而是异步发送数据。滑动窗口设置了异步发送数据量的大小。
- 窗口大小固定，分为发发送窗口，接收窗口
- 两个窗口都会根据收到ACK的情况剔除确认的数据，留出空间给新数据。
    
# 拥塞控制 #
发送方不能无脑的发数据给接收方，要考虑接收方处理能力。如果一直无脑的发数据给对方，但对方处理不过来，那么就会导致触发重发机制，从而导致网络流量的无端的浪费。、
为了解决这种现象发生，TCP 提供一种机制可以让「发送方」根据「接收方」的实际接收能力控制发送的数据量，这就是所谓的流量控制。我们可以通过滑动窗口的大小来实现
