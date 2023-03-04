如何用 Redis 实现分布式锁的？

用 Redis 的 setnx 来实现分布式的锁

set ex px nx + 校验唯一随机值，再删除