### 什么是用户态和内核态？用户态和内核态是如何切换的？

用户态和内核态是一个操作系统概念，可以认为内核是一个资源管理者，管理不同的用户使之互相隔离，用户态则是用户自己能掌控的小空间，需要内存则要向管理者申请。

从硬件的角度上来说，用户态和内核态可能就只差一个bit，这个bit设为1则能使用某些指令(汇编级别),否则不能使用。

切换有几种可能：
系统调用：
用户态主动调用某个指令(mit6.s081中是ecall)，之后便会切换到特定的代码区间进行上下文保存
中断/异常：
用户态被动的跳入中断表，同样也要进入特定的上下文保存代码区间

在切换的过程中会记录下切换的原因(用一个寄存器应该就行了)，在内核中可以进行不同的处理

参考：https://www.zhihu.com/question/306127044/answer/555327651