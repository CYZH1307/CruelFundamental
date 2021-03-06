## 2022/06/14

### 什么是用户态和内核态？用户态和内核态是如何切换的？

**用户态**：用户态相较于内核态有较低的执行权限，很多操作是不被操作系统允许的

**内核态**：拥有更高级的权限

**切换状态**：

- 系统调用：用户态进程通过系统调用申请使 用操作系统提供的服务程序完成工作
- 异常：当CPU在执行运行在用户态下的程序时，发生了某些事先不可知的异常，这时会触发由当前运行进程切换到处理此异常的内核相关程序中
- 外围设备中断：当外围设备完成用户请求的操作后，会向CPU发出相应的中断信号，这时CPU会 暂停执行下一条即将要执行的指令转而去执行与中断信号对应的处理程序，如果先前执行的指令是用户态下的程序，那么这个转换的过程自然也就发生了由用户态到内核态的切换