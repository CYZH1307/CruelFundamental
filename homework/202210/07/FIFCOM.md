## 时间片轮转调度算法中 时间片是如何校准时间的？

首先介绍下[Round-Robin Scheduling](https://en.wikipedia.org/wiki/Round-robin_scheduling)

当进程使用完一个时间片(Timing Quanta)时，由系统的计时器发出一个时钟中断(Timer Interrupt)。调度器收到时钟中断后，暂停该进程并放入队尾，取出下一个进程并分配时间片

> [《深入浅出理解Linux内核》 5.2 The Timer Interrupt Handler](https://www.oreilly.com/library/view/understanding-the-linux/0596000022/0596000022_ch05-5-fm2xml.html) 
> 
> Each occurrence of a timer interrupt triggers the : Determines how long the current process has been running on the CPU and preempts it if it has exceeded the time allocated to it.
