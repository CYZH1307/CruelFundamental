# 进程与线程的同步

## 进程/线程同步的方法

1. 临界区 (critical section)
   - 多线程串行，依次访问公共资源
   - 只能同步本进程内的线程，不可跨进程
2. 互斥量 (mutex)
   - 互斥资源的数量只有一个；通过互斥量名字打开；拥有互斥对象的线程才具有访问资源的权限
   - 可跨进程同步，进程内的线程同步建议用临界区
3. 信号量 (Semaphore)
   - 适合有限数量（>=1）的资源，限制同一时刻访问该资源的最大线程数
   - 可跨进程同步，使用socket程序中的线程同步（如对网站的访问用户数量进行限制）
   - 必须有公共内存，不能用于分布式操作系统
4. 事件 (Event)
   - 用来通知线程有一些事件已发生，从而启动后继任务
   - 可跨进程同步



## 进程同步和线程同步有什么区别

没什么区别。信号量一般用于多进程同步；临界区、互斥量等更多用于线程同步。

Reference: https://stackoverflow.com/questions/4623335/thread-synchronization-vs-process-synchronization

```
Semaphores are generally what are used for multi process synchronization in terms of shared memory access, etc.

Critical sections, mutexes and conditions are the more common tools for thread synchronization within a process.

Generally speaking, the methods used to synchronize threads are not used to synchronize processes, but the reverse is usually not true. In fact its fairly common to use semaphores for thread synchronization.
```



## 进程有哪几种状态

- `NEW`- The process is being created.
- `READY`- The process is waiting to be assigned to a processor.
- `RUNNING`- Instructions are being executed.
- `WAITING`- The process is waiting for some event to occur(such as an I/O completion or reception of a signal).
- `TERMINATED`- The process has finished execution.

