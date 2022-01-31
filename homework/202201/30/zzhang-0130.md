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

