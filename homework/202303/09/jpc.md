# 讲讲QUIC的流量控制和拥塞控制
**QUIC 实现流量控制的方式**：

Stream 级别的流量控制：每个 Stream 都有独立的滑动窗口，所以每个 Stream 都可以做流量控制，防止单个 Stream 消耗连接的全部接收缓冲。

Connection 流量控制：限制连接中所有 Stream 相加起来的总字节数，防止发送方超过连接的缓冲容量。

**QUIC拥塞控制的方式**：

QUIC 协议当前默认使用了 TCP 的 Cubic 拥塞控制算法：慢开始、拥塞避免、快重传、快恢复策略。

QUIC 是处于应用层的，应用程序层面就能实现不同的拥塞控制算法，不需要操作系统，不需要内核支持。这是一个飞跃，因为传统的 TCP 拥塞控制，必须要端到端的网络协议栈支持，才能实现控制效果。而内核和操作系统的部署成本非常高，升级周期很长，所以 TCP 拥塞控制算法迭代速度是很慢的。而 **QUIC 可以随浏览器更新，QUIC 的拥塞控制算法就可以有较快的迭代速度**。