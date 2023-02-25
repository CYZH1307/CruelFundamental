Redis 内存满了，会发生什么？

Redis 8种内存淘汰策略

volatile-lru
当内存不足以容纳新写入数据时，从设置了过期时间的key中（volatile）使用LRU（最近最少使用）算法进行淘汰

allkeys-lru
当内存不足以容纳新写入数据时，从所有key中（allkeys）使用LRU（最近最少使用）算法进行淘汰

volatile-lfu
当内存不足以容纳新写入数据时，在过期的key中（volatile）使用LFU（最少使用频率）算法进行淘汰

allkeys-lfu
当内存不足以容纳新写入数据时，从所有key中（allkeys）使用LFU（最少使用频率）算法进行淘汰

volatile-random
当内存不足以容纳新写入数据时，从设置了过期时间的key中（volatile）随机淘汰数据

allkeys-random
当内存不足以容纳新写入数据时，从所有key中（allkeys）随机淘汰数据

volatile-ttl
当内存不足以容纳新写入数据时，从设置了过期时间的key中（volatile）根据过期时间进行淘汰，越早过期的优先被淘汰

noeviction
默认策略，当内存不足以容纳新写入数据时，新写入操作会报错