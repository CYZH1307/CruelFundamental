TCP 可以限流吗

滑动窗口

通过控制滑动窗口的大小即可控制速率。
$$
throughput<\frac{RWIN}{RTT}
$$
RWIN为窗口大小 RTT为往返时间

控制RWIN即可控制速率