当多个进程协同完成一些任务时，不同进程的执行进度不一致，需要操作系统干预，在特定的同步点对所有进程同步。

方法：互斥锁，读写锁，条件变量，记录锁，信号量，屏障。

同一进程的线程之间具有相同的地址空间，线程同时修改同一数据或是协作的线程设置同步点时，需要使用一些线程同步方法。

方法：互斥锁，读写锁，条件变量，信号量，自旋锁

屏障。

区别？

进程之间地址空间不同，同步时需要将锁放在多进程共享的空间。而线程之间共享相同地址空间，同步时把锁放在所属的同一进程空间即可。
