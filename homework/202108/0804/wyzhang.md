# Semaphore 的应用场景和实现原理

Semaphore 适用于有明确访问数量限制的场景。

sample 1: 数据库连接池。 对于这个场景，因为链接池一般有个上限，当达到上限之后，就不能继续新增了，只能等前面的链接断了以后才能获得新的数据库连接。

sample 2: 停车场问题，因为车位是有限的，所以如果达到了容量的上限，那就只能等到车位有空余之后，才能让后面的车辆进入。

Semaphore 的常用方法有：

``` java
acquire() // 获取一个token，在获取到 token之前，一直处于阻塞状态
acquire(int permits) // 同上
tryAcquire() // 尝试获得token，返回token成功或者失败，不阻塞线程
release() // 释放一个 token
availablePermits() //返回可用的 tokens 数量。
```

## 实现原理

是一个非公平锁的同步阻塞队列(AQS)。

