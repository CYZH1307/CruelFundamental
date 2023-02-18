Redis内存淘汰机制?

- 共8种策略应对内存达到threshold的情况



1. `Noeviction`: 返回错误，不删除任何键值
2. `allkeys-lru`: 尝试回收最少使用的键值（LRU算法)
   - 近似LRU：对少量keys取样，并回收其中访问时间最早的key，不一定是全局最早。
3. `volatile-lru`: 尝试回收最少使用的键值，但仅限于在过期键值集合中
4. `allkeys-random`: 随机回收
5. `volatile-random`:随机回收，但仅限于在过期键值集合中
6. `volatile-ttl`: 回收过期集合的键值，并优先回收存活时间较短的键值
7. `allkeys-lfu`: 回收最少使用频次的键值（LFU算法）
8. `volatile-lfu`: 回收最少使用频次的键值，但仅限于在过期键值集合中



