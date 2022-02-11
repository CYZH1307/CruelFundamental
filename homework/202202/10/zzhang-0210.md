什么是缓存击穿, 穿透, 雪崩? 如何解决?

### Cache penetration 缓存击穿

- 一个请求要访问的数据，缓存中没有，但数据库中有的情况。

There is no requested key in the cache and database.

Solution:

- Use bloom filter: not-existed keys will be rejected directly instead of burderning the database.
- If we cannot find the key in the database, store a null with short expire time (e.g., < 5 mins).
  - Attention: there might be attackers to send different not-exist keys in a short time.

### Cache breakdown 缓存穿透

一个请求要访问的数据，缓存和数据库中**都没有**，而用户短时间、高密度的发起这样的请求，每次都打到数据库服务上，给数据库造成了压力。

hot key => cache broke down.

Solution:

- If cache fails, use cache tool with the return value of successful operations (`SETNX`) to set the mutex key.
- When the operation returns success, then load db and reset cache; otherwise, retry the entire get cached method.

`SETNX`: set if not exists.

```
public String get(key) {
  String value = redis.get(key);
  if (value == null) {

    if (redis.setnx(key_mutex, 1, 3*60) == 1) { 
      // set 3 mins timeout to prevent from failing to load db when teh del op fails
      value = db.get(key);
      redis.set(key, value, expire_secs);
      redis.del(key_mutex);
    } else { // other thread has get this value
      sleep(50);
      get(key); // retry
    }

  } else {
    return value;
  }
}
```

### Cache avalanche 缓存雪崩

指缓存中大多数的数据在同一时间到达过期时间，而查询数据量巨大，缓存中没有，数据库中有。

Request different keys that not exist in cache but exist in database.

Solution:

- Random expire time.

