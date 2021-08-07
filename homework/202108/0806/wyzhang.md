# 0806 介绍一下 Java 中的 ConcurrentLinkedQueue，ArrayBlockingQueue，LinkedBlockingQueue

## ConcurrentLinkedQueue

- 使用 CAS 算法实现 -- 也是普通的非阻塞队列的实现方法
- null 不能入队列
- 如果有两个线程，一个写队列（pop 或者 push），一个读队列，这样是线程不安全的。需要加锁。

## ArrayBlockingQueue
- 阻塞数组队列 
- 大小是在初始化的时候确定的，不能修改

## LinkedBlockingQueue

- 阻塞队列 // 也就是当某个线程已经获取到资源之后，其他线程必须阻塞等待前面的线程释放资源。
- 性能比较差的原因也是在于阻塞。