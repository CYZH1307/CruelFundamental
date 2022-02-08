# Redis内存淘汰机制

### 1 简介

（1）概念：Redis内存淘汰机制区别Reids过期策略。Redis过期策略为删除过期键值对的策略。Redis内存淘汰机制，Redis运行内存超过Redis设置的最大内存阈值后，采用什么策略删除符合条件的键值对。

（2）使用贴士

- maxmemory存储内存阈值。使用config get maxmemory-policy查询当前Redis内存淘汰机制。
- 内存淘汰执行流程：客户端发送命令->服务器端查询maxmemory是否大于0->检查运行内存是否大于maxmemory->执行内存淘汰策略->结束
- 修改Redis内存淘汰策略：（1）通过config set maxmemory-poliy xxx策略。缺点重启Redis失效。（2）

### 2 内存淘汰策略

#### 早期版本Redis淘汰策略

（1）noeviction：不淘汰人恶化数据，当内存不足，新增操作报错，Redis默认策略。

（2）allkeys-lru：淘汰所有键值范围内，最久未使用的键值。

（3）allkeys-random：随机淘汰任意键值。

（4）volatile-LRU：淘汰设置了过期时间的键值范围内，最久未使用的键值。

（5）volatile-random：淘汰设置了过期时间的键值范围内，随机淘汰任意键。

（6）volatile-ttl：淘汰设置了过期时间的键值范围内，更早过期的键值。

#### Redis4.0增加了两种策略

（7）volatile-lfu：淘汰设置了过期时间的键值范围内，最少使用的键值。

（8）allkeys-lfu：淘汰所有键值范围内，最少使用的键值。

小结:

- allkeys/volatile-xxx 前缀确定了键值的范围，allkeys表示所有的键值中淘汰数据，volatile表示设置了过期键的键值中淘汰数据。
- allkeys/volatile-xxx 后缀表示在范围内用的策略，lru 最久未使用， random随机， ttl只有设置了过期时间才能用删除更早过期，lfu为最少使用键值。

### 3 内存淘汰算法

内存淘汰策略，除了随即删除和不删除，主要为LRU和LFU

#### LRU算法

（1）简介：Least Recently Used 最近最少使用，选择最近最久未使用的页面淘汰。

（2）LRU算法基础实现：主要基于链表，最新操作移到表头，内存淘汰时从链表末尾移除即可。

（3）Redis实现：使用一种近似LRU算法，为了更好节约内存，实现为给现有的数据结构添加一个额外的字段，用于记录此键值最后一次访问时间，Redis内存淘汰的时候，就会随机采用方式来淘汰数据，随机选取5个值(可配置)，最后淘汰最久没有使用的。

（4）缺点：没有考虑使用次数，如果该键值对很少使用到，只是近期使用了一次，那么也不会淘汰，因此引入了LFU。

### LFU算法

（1）简介：Least Frequently Used 最不常用的，根据总访问次数来淘汰数据，如果数据过去被访问多次，那么未来被访问的频率也更高。

（2）Redis实现：在Redis对象头中存有ldt(last decrement time)存储访问频次值越小频率越低 和 logc(logic counter) 上一次logc更新时间。

