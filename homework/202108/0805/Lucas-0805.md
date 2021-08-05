# 并发队列
- https://segmentfault.com/a/1190000039159377
- https://blog.csdn.net/baichoufei90/article/details/84405459

## 非阻塞
- 如果失败，返回null或抛出异常
- 调用者不会被阻塞
- ConcurrentLinkedQueue
- ConcurrentLinkedDeque

## 阻塞队列
- 队列已满，添加者阻塞
- 队列为空，删除者阻塞
- ArrayBlockingQueue，数组实现，线程安全，入队出队不能并发
- LinkedBlockingQueue，链表实现，入队出队可并发
- PriorityBlockingQueue，通过可重入锁实现线程安全，出队入队不可并发
- DelayQueue，PriorityQueue + Delayed 接口
- SynchronousQueue，循环CAS保证线程安全
- LinkedTransferQueue，NOW/ASYNC/SYNC/TIMED 四种模式
- LinkedBlockingDeque，双向队列
