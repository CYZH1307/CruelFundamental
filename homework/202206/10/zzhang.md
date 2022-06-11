# Linux 读写数据的整个流程，内核状态切换

读流程
1. user 发起读请求，触发系统调用`read()`函数，用户态切换为内核态
2. 文件系统查询Page Cache是否存在: 目录项 => inode→address_space => 页缓存树
3. 若Page Cache不存在 => 缺页中断，CPU向DMA发出控制指令
4. DMA 控制器将数据从主存或硬盘拷贝到内核空间（kernel space）的缓冲区（read buffer）
5. DMA 磁盘控制器向 CPU 发出数据读完的信号，由 CPU 负责将数据从内核缓冲区拷贝到用户缓冲区
6. 用户进程由内核态切换回用户态，获得文件数据

写流程
1. user 发起写请求，触发系统调用`write()`函数，用户态切换为内核态
2. 文件系统查询Page Cache是否存在，如果不存在则需要创建: 目录项 => inode => address_space => 页缓存树
3. Page Cache存在后，CPU将数据从用户缓冲区拷贝到内核缓冲区，Page Cache变为脏页（Dirty Page），写流程返回
4. 用户主动触发刷盘或者达到特定条件内核触发刷盘，唤醒pdflush线程将内核缓冲区的数据刷入磁盘
