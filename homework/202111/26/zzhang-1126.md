# Redis 数据类型和分布式锁

## Redis 数据类型

Reference: https://www.runoob.com/redis/redis-data-types.html

共五种：string, hash, list, set, zset (sorted set)



### String

- 最大能储存512MB，二进制安全（可以包含任何数据，比如jpg图片或者序列化的对象）。

- K/V 存储

- `SET` , `GET`

```
$> SET keyname "one string"
OK
$> GET keyname
"one string"
$>
```



### Hash

- String 类型的 field 和 value 的映射表，适合存储对象
- K/V 存储

- 每个hash可以储存 2^32 -1 K/V pair
- `HMSET`, `HGET`

```
$> HMSET hkeyname field1 "Hello" field2 "World"
OK
$> HGET hkeyname field1
"Hello"
$> HGET hkeyname field2
"World"
$>
```



### List

- 字符串列表
- 可以添加一个元素到列表的头或者尾
- `LPUSH`, `LPOP`, `LRANGE`, `RPUSH`, `RPOP`, ...
- 列表最多可储存 2^32 - 1 元素

```
$> lpush lkeyname redis
(integer) 1
$> lpush lkeyname mongodb
(integer) 2
$> lpush lkeyname rabbitmq
(integer) 3
$> lrange lkeyname 0 10
1) "rabbitmq"
2) "mongodb"
3) "redis"
$>
```



### Set 

- string类型的无序集合
- 通过哈希表实现，所以添加、删除、查找的复杂度都是O(1)
- `sadd`: `sadd key member`. 成功返回1，如果元素已经在集合中返回0.
- `smembers`
- 集合中最多可储存 2^32 - 1 元素

```
$> sadd skeyname redis
(integer) 1
$> sadd skeyname mongodb
(integer) 1
$> sadd skeyname rabbitmq
(integer) 1
$> sadd skeyname rabbitmq
(integer) 0
$> smembers skeyname
1) "redis"
2) "rabbitmq"
3) "mongodb"
$>
```



### zset (sorted set)

- string 类型的集合，不允许重复
- 不同的是每个元素都会关联一个 double 类型的 score。redis 通过分数为集合中的成员进行排序
- zset 的成员是唯一的,但 score 可以重复
- `zadd`: `zadd key score number` 添加元素到集合，元素在集合中则更新 score

```
$> zadd zkeyname 0 redis
(integer) 1
$> zadd zkeyname 0 mongodb
(integer) 1
$> zadd zkeyname 0 rabbitmq
(integer) 1
$> zadd zkeyname 0 rabbit mq
(integer) 0
$> zrangebyscore zkeyname 0 1000
1) "mongodb"
2) "rabbitmq"
3) "redis"
$>
```



## 分布式锁🔒

Reference: https://www.infoq.cn/article/dvaaj71f4fbqsxmgvdce

分布式锁是控制分布式系统或不同系统之间共同访问共享资源的一种锁实现，如果不同的系统或同一个系统的不同主机之间共享了某个资源时，往往需要互斥来防止彼此干扰保证一致性。比如秒杀系统，要保证一样东西只卖给了一个用户。



一个相对安全的分布式锁，需要具备特征：

- 互斥性：同一时刻锁只能被一个线程持有，执行临界区操作。
- 超时释放：避免死锁，防止不必要的线程等待和资源浪费。
- 可重入性：一个线程在持有锁的情况可以对其再次请求枷锁，防止锁在线程执行完临界区操作之前释放。
- 高性能和高可用：加锁和释放锁的过程性能开销要尽可能的低，同时也要保证高可用，防止分布式锁意外失效。



分布式锁实现例子：

- Memcached: 利用 add 原子性操作
- ZooKeeper: 利用 顺序临时接待你。watch机制：加锁失败就阻塞，直到获取到锁为止
- Chubby: 通过sequencer机制解决了请求延迟造成的锁失效问题。
- Redis: 利用 `SETNX` 原子性操作。



### Redis 单机实现分布式锁

1. 使用 `SETNX` 指令

   ```
   SETNX lock_resource_id lock_value
   do something
   DEL lock_resource_id
   ```

   问题：

   - 超时：如果获得锁的进程在业务逻辑处理过程中出现了异常，可能导致DEL一直不成功，该资源一直被锁着。

   解决方案：

   - 加额外Expire指令

   ```
   SETNX lock_resource_id lock_value
   EXPIRE lock_resource id 10
   do something
   DEL lock_resource_id
   ```

   问题：

   - 超时Again：由于 SETNX 和 EXPIRE 这两个操作是非原子性的， 如果进程在执行 SETNX 和 EXPIRE 之间发生异常，SETNX 执行成功，但 EXPIRE 没有执行，这把锁还是一直在。

2. 使用 `SET` 扩展指令

   使用扩展参数：

   - NX 表示只有当 lock_resource_id 对应的 key 值不存在的时候才能 SET 成功。保证了只有第一个请求的客户端才能获得锁，而其它客户端在锁被释放之前都无法获得锁。
   - EX 10 表示这个锁 10 秒钟后会自动过期，业务可以根据实际情况设置这个时间的大小

   ```
   SET lock_resource_id lock_value NX EX 10
   do something
   DEL lock_resource_id
   ```

   问题：

   - 锁被提前释放：A加了锁之后中间执行时间过长，锁被提前释放，剩下的代码没有串行执行。没有解决。

   - 锁被误删：A的锁被释放了之后，B拿到了锁；A回来把剩下代码跑完，把锁删了。解决方案：执行 DEL 释放锁之前对锁进行判断，验证当前锁的持有者是否是自己。但判断 value 和删除 key 是两个独立的操作，并不是原子性的，所以这个地方需要使用 Lua 脚本进行处理，因为 Lua 脚本可以保证连续多个指令的原子性执行。

     ```
     set lock_resource_id random_value nx ex 10
     do something
     if random_value == lock_resource_id.value
     	del lock_resource_id
     ```

3. 使用 Redisson 的分布式锁

   利用锁的可重入特性，让获得锁的线程开启一个定时器的守护线程，每 expireTime/3 执行一次，去检查该线程的锁是否存在，如果存在则对锁的过期时间重新设置为 expireTime，即利用守护线程对锁进行“续命”，防止锁由于过期提前释放。

   链接：https://github.com/redisson/redisson/wiki



### Redis 多机实现的分布式锁 Redlock

基于Redis 单机实现的分布式锁的问题：加锁只作用在一个节点。A从Master拿到了锁，Master节点故障，转到新Master，复制的时候，锁对应的Key没有同步到新Master上，B又可以从新Master上拿同一个资源的锁。



#### Redlock

假设有 N（N>=5）个 Redis 节点，这些节点完全互相独立，不存在主从复制或者其他集群协调机制，确保在这 N 个节点上使用与在 Redis 单实例下相同的方法获取和释放锁。



客户端获取锁的流程

1. 获取当前Unix时间 $$T_1$$，以毫秒为单位

2. 按顺序依次尝试从 5 个实例使用相同的 key 和具有唯一性的 value（例如 UUID）获取锁。当向 Redis 请求获取锁时，客户端应该设置一个网络连接和响应超时时间，这个超时时间应该小于锁的失效时间。例如锁自动失效时间为 10 秒，则超时时间应该在 5-50 毫秒之间。这样可以避免服务器端 Redis 已经挂掉的情况下，客户端还在一直等待响应结果。如果服务器端没有在规定时间内响应，客户端应该尽快尝试去另外一个 Redis 实例请求获取锁。

3. 客户端使用当前时间减去开始获取锁时间 $T_1$ 就得到 获取锁使用的时间 $T_2$。当且仅当从大多数（N/2+1，这里是 3 个节点）的 Redis 节点都取到锁，并且使用的时间小于锁失效时间时，锁才算获取成功。

4. 如果取到了锁，key 的真正有效时间 等于 有效时间 减去 获取锁所使用的时间$T_2$

5. 如果因为某些原因，获取锁失败（没有在至少 N/2+1 个 Redis 实例取到锁或者取锁时间已经超过了有效时间），客户端应该在所有的 Redis 实例上进行解锁（使用 Redis Lua 脚本）



客户端释放锁的流程

- 客户端向所有 Redis 节点发起释放锁的操作，包括加锁失败的节点。
  - 可能存在某个节点加锁成功后返回客户端的响应包丢失了，所以还是得向加锁失败的节点发释放锁请求。



其他注意事项：

- 为了避免 Redis 节点发生崩溃重启后造成锁丢失，从而影响锁的安全性，一个节点崩溃后不要立即重启，而是等待一段时间后再进行重启，这段时间应该大于锁的有效时间。

