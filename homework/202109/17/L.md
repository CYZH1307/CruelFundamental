
### 简述RedLock多节点分布式锁算法

## 应用：多个使用redis的服务保证同时同一用户只能有一个请求；

## Safety & Liveness：

1.Satefy: Mutual exclusion

2.Liveness property A: Deadlock free.

3.Liveness property B: Fault tolerance. 

## redis实例中实现分布式锁:

Assume N个Redis master，we need to assume that we use the same method to acquire and set locks on N instances as well as one instance.

In order to acquire the lock:

gets the UNIX time stamp in milliseconds.
acquires the lock in sequential order, using the same key name. 
implements a timeout attribute which is small duration of time compared to the total lock auto-release time in order to acquire it. 
By doing this, we prevent the client from blocked in order to talk with a Redis node which is offline. 


The client takes note of how much time elapsed to acquire the lock.
the lock is considered to be acquired if the client was able to acquire the lock in the majority of the instances and the total time elapsed to acquire the lock is less than lock validity time.

all the instances will be unlocked if the client failed to acquire the lock for some reason
