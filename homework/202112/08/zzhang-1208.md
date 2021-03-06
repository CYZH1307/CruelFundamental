# 快表和多级页表

快表和多级页表分别解决了页表管理中很重要的两个问题。简单介绍一下吧！

Reference: https://zhuanlan.zhihu.com/p/152119007

### 多级页表

为了解决单级页表页表项存储大的问题：

- 页表一定要覆盖全部虚拟地址空间，不分级的页表需要有 100 多万个页表项来映射，而二级分页则只需要 1024 个页表项。

- 在 32 位的环境下，虚拟地址空间共有 4GB，假设一个页的大小是 4KB，那么就需要大约 100 万个页，每个「页表项」需要 4 个字节大小来存储，那么整个 4GB 空间的映射就需要有 `4MB` 的内存来存储页表。

- 在上述例子中，可以将页表（一级页表）分为 `1024` 个页表（二级页表），每个表（二级页表）中包含 `1024` 个「页表项」，形成**二级分页**。如果某个一级页表的页表项没有被用到，不需要创建这个页表项对应的二级页表。



64 位系统分成四级目录：

- 全局页目录项 PGD（*Page Global Directory*）

- 上层页目录项 PUD（*Page Upper Directory*）

- 中间页目录项 PMD（*Page Middle Directory*）

- 页表项 PTE（*Page Table Entry*）

  

### 快表

TLB（*Translation Lookaside Buffer*）页表缓存

为了解决虚拟地址到物理地址的转换的overhead高的问题：利用 **locality** 解决。

- 把最常访问的几个页表项存到cache里
-  CPU里封装了内存管理单元（*Memory Management Unit*）芯片 用来完成地址转换和 TLB 的访问与交互。CPU 寻址时，会先查 TLB，如果没找到，才会继续查常规的页表。

