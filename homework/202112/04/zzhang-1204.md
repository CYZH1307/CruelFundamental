# 死锁

## 死锁是怎样产生的

四个必要条件

- 互斥：资源一次只能有一个进程使用
- 非抢占：进程获得资源后，不能被其他进程夺走
- 占有并等待：进程申请所需的一部分资源后，在等待其他资源时继续占用已分配的资源
- 循环等待：存在一种进程互相等待的环，每个进程都在等待下一个进程所持有的资源



## 死锁解决方法

解决上述四个条件中的至少一个，例如

- 一次申请所有资源
- 已占有不可剥夺资源的进程，请求新的资源得到不满足时，释放已经占有的所有资源



避免死锁：动态分配资源时，通过算法避免

- 如果进程请求的资源会导致死锁，系统拒绝

- 如果对一个资源的分配会导致下一步的死锁，系统拒绝

  





