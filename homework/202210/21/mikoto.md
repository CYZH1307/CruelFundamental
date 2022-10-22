## Linux /dev /proc 存储哪些数据?

/proc 目录虚拟文件系统，讲呢he与进程状态归档为文本文件。 linux系统上的/proc目录是一种虚拟文件系统，存储当前内和运行状态的一系列特殊文件，用户可以通过查看这一系列文件查看有关系统硬件和当前运行
进程的信息，甚至可以通过改变某些文件来改变内核的运行状态。

简单目录介绍：
- /proc/meminfo 查看内存信息
- /proc/loadavg top 和 uptime的平均数就记录在此
- /proc/uptime uptime是出现的咨询
- /proc/cpuinfo 处理器信息，类型厂家型号等
- /proc/cmdline 加载kernel时的相关参数，可以用于了解系统如何启动
- /proc/filesystem 系统当前加载的文件系统
- /proc/interrupts 目前系统上面的IRQ分配状态
- /proc/ioports 目前系统各装置的I/O地址
- /proc/kcore 内存大小
- /proc/modules linux加载的模块列表（类似驱动程序）
- /proc/mounts 系统挂载的数据，mount出来的就是这个
- /proc/swaps 系统加载的内存在哪
- /proc/version 和新版本 uname -a显示的内容
