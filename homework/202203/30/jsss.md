# TCP可以限速吗

TCP协议可以根据接收方返回的滑动窗口值来协调发送方的发送速率. 发送方发送窗口大小 = min(拥塞窗口大小, 接收方发送窗口大小). 其利用滑动窗口可以主动控制发送窗口的大小, 而拥塞窗口一定程度上可以表示网络的拥塞情况, 是TCP拥塞控制方法依据网络的情况动态调整发送窗口大小的方法.