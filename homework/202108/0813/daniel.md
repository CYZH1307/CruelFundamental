# mmap


## 基本原理
* linux 每个进程 会有一个 task struct 来维护进程运行时的信息
* 进程通过 mm_struct 同时维护了 mmap 和 mm_rb 两个结构体来控制 virutal memory areas (aka. vma)
    * mmap 是链表, 用于遍历整个 vma
    * mm_rb 是红黑树, 用于快速查找
* mmap 所做的就是创建一个 virtual memory area,  陷进内核后对应do_mmap()
* 当创建完映射后, 并不会将文件加载到内存中, 而是在真正要访问的时候通过 page fault 来触发加载

## mmap 好在哪里?

以文件读取举例:
* 普通 syscall 会将先文件从 磁盘读取到 内核态的 page cache 中, 由于用户态进程无权限访问, 所以还需要将 page cache 中的内容拷贝到用户态的buffer 中
* mmap 是直接创建了 vma, 其映射了文件和进程的虚拟地址块, 缺页时, 会直接将数据拷贝到用户态buffer


## 为什么很多数据库用 mmap 失败了?
虽然 mmap 减少了拷贝, 但是操作系统对上层应用的内存使用pattern 并不清楚, 所以指望靠 mmap 来达到理想的优化是不太现实的.