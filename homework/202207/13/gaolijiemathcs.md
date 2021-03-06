### 什么是IPC(Inter Process Communication)? 常见的IPC方法有哪些?

IPC进程间通信。因为不同进程有各自不同的地址空间，因此进程之间交换数据必须通过内核，内核提供的供给进程之间通信完成任务的机制简称为进程间通信。

常见IPC方法：

1. 管道(pipe)：又称匿名管道，半双工方式，数据单向流动，亲缘关系进程间使用。
2. 有名管道(namedpipe)：有名管道也是半双工，但是可以非亲缘关系的进程间通信。
3. 信号量(semophore)：计数器，控制多个进程对共享资源的访问，锁机制，也可以进程间和线程间的同步方式。
4. 消息队列：消息链表。
5. 信号(signa)：通知接收进程某个事件已经发生。
6. 共享内存：映射一段可以被其他进程访问的内存，一个进程创建多个进程可以访问。
7. 套接字：可以用于不同设备间进程通信。

