# 内核态和用户态



内核态：

- 控制计算机的硬件资源，例如CPU调度，分配内存资源，提供稳定的环境供应用程序运行。
- 可以执行所有CPU指令，访问任意地址的内存。

用户态：

- 提供应用程序运行的空间。
- 没有对硬件的直接控制权限，也不能直接访问地址的内存。
- 通过调用系统接口(System Call APIs)来达到访问硬件和内存。





### 如何实现内核态和用户态的切换

1. 系统调用

   用户态进程通过*系统调用*主动切换到内核态度，如`fork()` 执行了一个创建新进程的系统调用

2. 中断

   当外围设备完成用户请求的操作后，会向CPU发出相应的中断信号，此时CPU就会转去执行中断信号对应的处理程序。如果之前的指令是用户态的，即完成用户态到内核台的转换

3. 异常

   如果CPU在执行用户态下的程序，发生了异常，如缺页异常，就会切换到处理该异常的内核态相关程序中。