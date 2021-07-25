## 进程, 线程, 协程

进程就是运行中的程序, Processes, in effect, are the living result of running program code

线程是进程内部的活动的实体. 线程拥有独立的: stack, PC, registers
线程是操作系统调度的最小单位. 但是同一进程间的线程是共享虚拟内存地址空间的.

协程，是在应用层的一种并发模型，他避免了内核上下文切换的额外耗费, 由应用层去调度.
比如 Erlang / Elixir 里的 process, 只是用户层的进程, 内存消耗也远小于线程, 非常轻量, 由 Beam 根据 reduction 来调度. 

