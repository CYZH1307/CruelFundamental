# Linux 文件描述符
- https://zhuanlan.zhihu.com/p/143430585

## 文件描述符
- 系统维护了三张文件描述符表，系统级，进程级，文件系统 inode 表
- 文件描述符，进程级文件表鼠标的索引，相当于数组的下标
- task_struct 进程描述符，维护进程信息
- files_struct* files 进程级的文件描述表
- TODO-系统级文件描述符表

## inode 表
- 系统为每个文件和目录维护一个 inode表
- 包含文件类型（目录，套接字等），权限，文件锁，大小等信息
- 存储在磁盘上，内核在内存中也维护了一个副本
- 一般使用内存中的 inode 表
- 和磁盘上的相比，多了引用计数，设备号等信息

## 文件链接
- 硬链接，文件名和 inode 一一对应
- 软链接，文件名和 inode 一对多
