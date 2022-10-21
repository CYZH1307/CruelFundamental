/proc/目录
虚拟文件系统，将内核与进程状态归档为文本文件（系统信息都存放这目录下）

Linux系统上的/proc目录是一种文件系统，即proc文件系统。与其它常见的文件系统不同的是，/proc是一种伪文件系统（也即虚拟文件系统），存储的是当前内核运行状态的一系列特殊文件，用户可以通过这些文件查看有关系统硬件及当前正在运行进程的信息，甚至可以通过更改其中某些文件来改变内核的运行状态。

目录	描述
/proc/meminfo	查看内存信息
/proc/loadavg	还记得 top 以及 uptime 吧？没错！上头的三个平均数值就是记录在此！
/proc/uptime	就是用 uptime 的时候，会出现的资讯啦！
/proc/cpuinfo	关于处理器的信息，如类型、厂家、型号和性能等。
/proc/cmdline	加载 kernel 时所下达的相关参数！查阅此文件，可了解系统是如何启动的！
/proc/filesystems	目前系统已经加载的文件系统罗！
/proc/interrupts	目前系统上面的 IRQ 分配状态。
/proc/ioports	目前系统上面各个装置所配置的 I/O 位址。
/proc/kcore	这个就是内存的大小啦！好大对吧！但是不要读他啦！
/proc/modules	目前我们的 Linux 已经加载的模块列表，也可以想成是驱动程序啦！
/proc/mounts	系统已经挂载的数据，就是用 mount 这个命令呼叫出来的数据啦！
/proc/swaps	到底系统挂加载的内存在哪里？呵呵！使用掉的 partition 就记录在此啦！
/proc/partitions	使用 fdisk -l 会出现目前所有的 partition 吧？在这个文件当中也有纪录喔！
/proc/pci	在 PCI 汇流排上面，每个装置的详细情况！可用 lspci 来查阅！
/proc/version	核心的版本，就是用 uname -a 显示的内容啦！
/proc/bus/*	一些汇流排的装置，还有 U盘的装置也记录在此喔！



### `/dev/`目录

- 设备文件分为两种：块设备文件(b)和字符设备文件(c)
- 设备文件一般存放在/dev目录下

> dev是设备(device)的英文缩写。/dev这个目录对所有的用户都十分重要。因为在这个目录中包含了所有Linux系统中使用的外部设备。但是这里并不是放的外部设备的驱动程序，这一点和windows,dos操作系统不一样。它实际上是一个访问这些外部设备的端口。我们可以非常方便地去访问这些外部设备，和访问一个文件，一个目录没有任何区别。

| 目录           | 描述                        |
| :------------- | :-------------------------- |
| /dev/hd[a-t]   | IDE设备                     |
| /dev/sd[a-z]   | SCSI设备                    |
| /dev/fd[0-7]   | 标准软驱                    |
| /dev/md[0-31]  | 软raid设备                  |
| /dev/loop[0-7] | 本地回环设备                |
| /dev/ram[0-15] | 内存                        |
| /dev/null      | 无限数据接收设备,相当于黑洞 |
| /dev/zero      | 无限零资源                  |
| /dev/tty[0-63] | 虚拟终端                    |
| /dev/ttyS[0-3] | 串口                        |
| /dev/lp[0-3]   | 并口                        |
| /dev/console   | 控制台                      |
| /dev/fb[0-31]  | framebuffer                 |
| /dev/cdrom     | => /dev/hdc                 |
| /dev/modem     | => /dev/ttyS[0-9]           |
| /dev/pilot     | => /dev/ttyS[0-9]           |
| /dev/random    | 随机数设备                  |
| /dev/urandom   | 随机数设备                  |