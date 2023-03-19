## 03月19日

### 为什么TCP每次建立连接时，初始化序列号都要不一样？

> 可以很大程度上的减少历史报文的影响，初始化序列号 ISN 随机生成算法：ISN = M + F(localhost, localport, remotehost, remoteport)。可以避免重复SYN。

- M是一个计时器，这个计时器每隔 4 微秒加1。
- F 是一个 Hash 算法，根据源IP、目的IP、源端口、目的端口生成一个随机数值