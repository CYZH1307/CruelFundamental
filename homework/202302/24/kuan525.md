## Redis 内存满了，会发生什么？
出发内存淘汰机制，共有八种。
- 不进行数据淘汰的策略，当redis内存满了的时候，不在提供服务，拒绝访问
- 进行内存淘汰的策略
	- 针对过期时间淘汰
		- volatile-random：随机淘汰过期的数据
		- volatile-ttl：优先淘汰更早过期的数据
		- volatile-lru：优先淘汰最久没有使用的数据
		- volatile-lfu：优先淘汰最少使用的数据
	- 针对所有内存淘汰
		- allkeys-random：随机淘汰所有的内存中的一部分25%
		- allkeys-lru：淘汰整个键值中最久没有使用的部分
		- allkeys-lfu：淘汰整个键值中最少使用的部分