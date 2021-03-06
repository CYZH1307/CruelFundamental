# 什么是用户态和内核态？用户态和内核态是如何切换的？

两者都是程序的状态，区别在于可访问的CPU指令集不同。相比用户态，内核态是权限更高、能访问更高级别指令集、能操作硬件资源的操作系统状态。容易造成系统崩溃，因此系统不能一直处于内核态。  

两者的切换，通常发生在程序需要访问内核指令（如read、write）的场景，如系统调用、中断等。一次完整的切换是用户态→内核态→用户态，系统资源开销较大，顺序如下：

 - 保留用户态现场（上下文、寄存器、用户栈等）
 - 复制用户态参数，用户栈切到内核栈，进入内核态
 - 额外的检查（因为内核代码对用户不信任）
 - 执行内核态代码
 - 复制内核态代码执行结果，回到用户态
 - 恢复用户态现场（上下文、寄存器、用户栈等）




