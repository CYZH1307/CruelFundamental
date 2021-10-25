# cpp多线程 #
1.**创建线程**：调用C11标准库的thread 类，thread th1(func, arg1, arg2, ...)，其中th1是线程实例名，func是初始化该实例的函数指针，argi是func的入参。初始化完成后，th1马上开始执行。  
2.**删除线程**：调用C11标准库的thread 类的析构。  
3.**连接线程**：调用thread 类中的join()或detach()方法，注意，在任意线程生命周期里面必须至少调用这两者之一。建立th1之后，调用th1.join()可让建立th1的线程停止，等待th1执行完毕再在原地开始执行。  
4.**线程锁**：一般使用互斥锁mutex和读写锁。
    i.互斥锁要先实例化全局变量mutex mu，然后在线程函数中需要保护的操作开始时mu.lock()，结束时mu.unlock()，可保证线程执行时对资源的读写独占，不会被其他线程读写。  
    ii.读写锁可以实现读共享、写独占，要先实例化全局变量shared_mutex rw，然后读锁是rw.lock_shared()和rw.unlock_shared()，写锁是rw.lock()和rw.unlock()
