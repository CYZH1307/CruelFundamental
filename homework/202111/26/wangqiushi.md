redis 分布式锁实现

setnx+setex：存在设置超时时间失败的情况，导致死锁 set(key，value，nx，px)：将setnx+setex变成原子操作。问题是：-任务超时，锁自动释放，导致并发问题，使用redisson解决。-加锁和释放锁，不是同一个线程的问题。在value中存入uuid，删除锁时判断该标识(使用lua保证原子操作)。 -不可重入，redisson解决。 -异步复制可能导致锁丢失，使用redLock解决。

1.顺序向五个节点请求加锁。2.根据一定的超时时间来推断是不是跳过该节点。3.三个节点加锁成功且花费时间小于锁的有效期。4.认定加锁成功。

Redis支持数据结构：

String, List, Hash, Set, Sorted set, bitmap,
