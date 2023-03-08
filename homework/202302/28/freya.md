## 如何用 Redis 实现分布式锁的？

利用命令：``SETNX``

``` SETNX key value```

该操作会有两个可能得返回值：
- 1：代表赋值成功（拿到锁）
- 0：代表赋值失败，key已经存在（锁被持有）

不同的进程对同一个key执行SETNX，返回1即为上锁成功，并在结束后删掉该key（解锁）