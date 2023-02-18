# Google BBR 拥塞控制算法原理
- https://cloud.tencent.com/developer/article/1482633
- https://kernel.taobao.org/2019/11/bbr-vs-cubic-in-datacenter-network/

## 需求
- 油管应用吞吐量提升了4%
- 报文的来回时延降低了33%，用户体验更好，RTT，Round Trip Time
- 重新缓存的平均时间提升了11%

## 实现
- Linux 2.6.19 内核的拥塞控制算法，CUBIC
- Linux 4.19 内核的拥塞算法控制，BBR
- UDP HTTP3 也使用 BBR
- TCP 面向字符流，应用层客户端来读写字符流
- IP 层是基于块的，将 TCP 字符流，拆分成块
- 路由输入大于输入，容易发生拥塞
- 拥塞控制：慢启动，拥塞避免，快速重传，快速恢复
- BBR 周期性的探测带宽瓶颈
