# Linux mmap 原理及其使用
- https://www.cnblogs.com/huxiao-tee/p/4660352.html

## 概念
- 一种内存映射文件的方式，磁盘文件地址到虚拟内存地址
- 不必调用文件读写的接口，实现进程间的文件共享

## 过程
- 进程在用户空间调用函数寻找一段满足大小要求的连续的虚拟空间
- void* mmap(void* start, size_t length, int prot, int flags, int fd, off_t offset)
- 给虚拟区分配一个vm_area_struct接口，并初始化
- 将 vm_area_struct 结构 插入进程的虚拟地址区域链表
- 调用内核的 mmap(struct file* filep, stuct vm_area_struct* vma) 函数，不同于用户空间的函数
- 内核函数通过虚拟文件系统 inode 模块定位到文件磁盘的物理位置
- 使用 remap_pfn_range 函数建立页表，实现文件地址到虚拟内存地址的映射
