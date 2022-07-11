#### 什么是MESI协议？

MESI协议是基于失效的多核CPU的多级缓存(如L1 Cache)一致性协议。

四个字母指的是cache line的状态。

| 状态  | 描述  |
| :------------: | :------------: |
|Modified|该cache line有效，但和内存中的数据不一致，数据仅存在于本cache中|  
|Exclusive|有效，但数据仅存在于本cache中，数据与内存中一致|
|Shared|有效，数据存在于多个cache中，数据于内存中一致|
|Invalid|该cache line无效|

1. Modified状态说明相应的CPU核心进行了写操作，同时也表示该数据不存在于其他CPU核心的L1 Cache中，即该最新的cache line是独占的。如果需要将数据传递给其他L1 Cache或者写回内存中，需要在置换(reuse cache line)前完成
2. Exclusive状态于Modified类似，只不过CPU核心还没有修改cache line的数据，可以直接reuse cache line
3. Shared状态可以直接reuse cache line
4. Invalid状态的cache line是空的。当新数据要进入L1 Cache时优先选择Invalid状态的，可以提高性能。
