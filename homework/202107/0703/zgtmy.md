死锁的条件，如何解决？
死锁：死锁是指有多个进程被阻塞，因为这些进程中的每一个都获取了一些资源并且在等待获取已经被其他进程持有的资源。
死锁的四个必要条件：
1.互斥：一个或多个资源是互斥的，同一个时间只有一个线程可以使用
2.持有和等待：一个进程持有至少一个资源并且等待其他资源
3.禁止抢占：一个资源不会被获得，一个进程不能获取到已经被其他进程占有的资源，除非持有这个资源的进程释放了这个资源
4.循环等待：多个进程在一个环中，每一个进程都在等待其他进程释放自己要获取的资源。
死锁的发现：操作系统使用资源调度器对资源分配给了哪个进程进行监测，所以当死锁发生的时候，资源调度器会发现。
如何解决？
三种方法
1.死锁预防：
操作系统会在事物执行之前对事物进行检查，以确保事物不会产生死锁，如果会产生死锁的事物将不会被执行。
操作系统会通过一系列的方法确保死锁的四个必要条件至少有一个不满足。
我们需要在程序执行之前就知道程序所需要的资源，然后用银行家算法来避免死锁。
2.死锁的避免：
死锁的避免需要额外的信息，比如一个进程所需要宣称它对每种资源所需要的最大数量，死锁避免算法以及这些资源将被如何使用
使用死锁排除算法，动态的评估资源的使用状态，来避免进程间循环等待的状态。

当系统中每个资源的只要一个实例
使用资源分配图
循环等待是产生死锁的充要条件

当系统中的每个资源有多个实例时
循环等待是死锁产生的必要非充分条件
可以使用银行家算法

