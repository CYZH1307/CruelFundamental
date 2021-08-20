# TCP congestion control

## meta

* 慢启动
* 拥塞避免
* 拥塞发生策略

### 慢启动
开始是, 仅能发送固定 MSS 的长度的数据, e.g. 10 * 1500 大约 15KB, 随着 ack 的到来和时间采样的计算, 逐步提升拥塞窗口 (cwnd)

### 拥塞避免

当拥塞窗口超过一个上限时, 会减慢拥塞窗口的增长速率, 
* 收 ACK: cwnd = cwnd + 1/cwnd 
* 过 RTT: cwnd = cwnd + 1

### 拥塞恢复

在 RTO (retransmission timeout时) 或者收到多个重复的ACK 时, 认为拥塞可能发生, 会启动拥塞恢复算法, 降低 cwnd 和窗口上限

其他算法:
* TCP Vegas
* TCP BIC
* TCP WestWood