# Redis的内存淘汰机制

由于内存空间有限，而数据一直在增加，因此缓存数据总会超过机器内存大小，因此需要设计合适的内存淘汰策略以腾出内存空间。

最新版本的Redis一共有8种内存淘汰机制：

1. noevition:不进行淘汰，内存不够即报错，如果存在爆内存的风险则不能采用此策略
2. allkeys-random：随即删除策略
3. allkeys-lru：使用LRU算法（最近最少使用）进行删除
4. allkeys-lfu：使用LFU算法（最不经常使用）进行删除

allkeys是指在所有数据中筛选，对应的还有在过期数据中筛选（volatile）

5. volatile-random：在过期数据中随机删除
6. volatile-lru：在过期数据中使用LRU删除
7. volatile-lfu：在过期数据中使用LFU删除

特别地针对过期数据还有一个：
8. volatile-ttl：根据过期时间先后删除，优先删除先过期的数据
