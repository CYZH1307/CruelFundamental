# Redis 特点

Redis: REmote DIctionary Server

- atomicity, consistency, isolation, durability 
- CAP: lacks availability
- master-slave 备份

- 支持 publish/subscribe 可以作为 message queue



 支持多种数据结构

1. string
2. hash
3. list
4. set
5. zset (sorted set)



应用场景：

Redis is extremely fast, making it perfectly suited for applications that are write-heavy, data that changes often, and data that naturally fits one of Redis’s data structures (for instance, analytics data). 



不适合应用的场景：

A scenario where you probably *shouldn’t* use Redis is if you have a very large dataset of which only a small part is “hot” (accessed often) or a case where your dataset doesn’t fit in memory.

