# 死锁

# 死锁的产生 #
四个必要条件，缺一不可：
    1.互斥：一个资源同时只能给一个进程使用。
    2.请求与保持条件：进程在手握资源A时再去请求资源B被阻塞，不会释放资源A。
    3.不可剥夺：持有资源的线程，在使用完毕该资源前不可被剥夺。
    4.循环等待：若干线程首尾相接形成等待资源的环。

# 解决死锁 #
    1.预防：在系统设计时破坏以上4个条件的任意一个。
    2.避免：使用合适的资源分配算法，如银行家算法，避免任意进程永久持有任意资源。
