# CPU 寻址

CPU寻址了解吗？ 

- 虚拟地址寻址 virtual addressing
- 用内存管理单元（MMU）完成虚拟地址到物理地址的转换



为什么需要虚拟地址空间？ 

- 把物理地址暴露给进程，容易破坏操作系统

- 需要分配连续内存，运行多个程序困难

  

什么是虚拟内存(Virtual Memory)？

- idealized abstraction of the storage resources that are actually available on a given machine which creates the illusion to users of a very large (main) memory.
- 把实际的存储抽象化，让进程认为有一块完整的很大的内存能用。
