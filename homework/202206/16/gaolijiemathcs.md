### CPU 分几级缓存，大小一般分别多少；讲一下访问各级缓存需要的时间，需要多少 CPU Cycle 的数量级

#### CPU 分几级缓存

现在的CPU中有好几个等级的缓存。通常L1和L2缓存都是每个CPU一个的, L1缓存有分为L1i和L1d，分别用来存储指令和数据。L2缓存是不区分指令和数据的。L3缓存多个核心共用一个，通常也不区分指令和数据。 还有一种缓存叫TLB，它主要用来缓存MMU使用的页表，通常我们讲缓存（cache)的时候是不算它的。

#### 讲一下访问各级缓存需要的时间，需要多少 CPU Cycle 的数量级

L1 cache: 3 cycles

L2 cache: 11 cycles

L3 cache: 25 cycles

Main Memory: 100 cycles



ref:https://zhuanlan.zhihu.com/p/31875174