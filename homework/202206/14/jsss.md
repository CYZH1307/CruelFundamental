# 什么是用户态和内核态？用户态和内核态是如何切换的？

操作系统的运行状态分为用户态和内核态, 分别有用不同的执行权限. 比如必须在内核态读写磁盘、网络IO等等. 这样做的目的一是安全性, 另外一个是保证互斥正确访问(操作系统会调度).


用户态和内核态的切换：

1. 系统调用: 如读写磁盘, 网络IO, 系统切换到内核态进行数据读写和设备访问等操作.
2. 中断: 如缺页中断, 系统切换到内核态执行中断处理程序.
3. 异常：如除0异常, 系统切换到内核态执行异常处理程序.
