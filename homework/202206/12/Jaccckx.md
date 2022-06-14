# Cache命中降低的方法

1. 缓存器中的三种不命中情况

1. 1. 必定造成的不命中：初始缓存为空的状态
   2. 由容量导致的不命中：本质上cache只是个更小的内存而已，总会有不命中的情况
   3. 由冲突导致的不命中（Conflict Miss）：有些缓存被设计成两路相关联（Two-way Associ）或是四路相关联（Four-way Associ），在这些组（Sets）里经常会发生数据放满了，放不下其他相关的数据，这种因为组里的冲突而发生的不命中，就叫做 Conflict Miss

1. 三种优化情况

- 增大缓存总容量（Cache Capacity）来减少错失率

- - 综合电路费用，命中时的任务量（寻路时间）

- 增大块长度（Block Size）来减少错失率

- - 但是组相联的块数会变少，会导致Conflict Miss增大，但是寻路时间减少

- 增加相关联性（Higher Associative）来减少错失率

- - 组相连的电路特别昂贵
  - 导致Capacity Miss增加



- 推荐文章

- - https://zhuanlan.zhihu.com/p/347651405
