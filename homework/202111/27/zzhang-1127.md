# Redis Cache

redis扛不住怎么办，不允许限流降级

 Reference: https://programmer.help/blogs/redis-cache-penetration-cache-breakdown-and-cache-avalanche.html, https://www.cnblogs.com/thisiswhy/p/15089432.html



### Cache penetration

There is no requested key in the cache and database.

Solution:

- Use bloom filter: not-existed keys will be rejected directly instead of burderning the database. 
- If we cannot find the key in the database, store a null with short expire time (e.g., < 5 mins).
  - Attention: there might be attackers to send different not-exist keys in a short time.



### Cache breakdown

hot key => cache broke down.

Solution:

- If cache fails, use cache tool with the return value of successful operations (`SETNX`) to set the mutex key.
- When the operation returns success, then load db and reset cache; otherwise, retry the entire get cached method.



`SETNX`: set if not exists. 

```java
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





### Cache avalanche 

Request different keys that not exist in cache but exist in database.

Solution:

- Random expire time.





