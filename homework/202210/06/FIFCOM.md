## bios到kernel的启动过程(Linux)

BIOS->硬件检测，查找加载磁盘上的MBR
MBR->存储BootLoader信息，加载GRUB
GRUB->查找并加载Kernel
Kernel->装载驱动，挂载rootfs，执行/sbin/init
Init->OS初始化，执行RunLevel相关程序
RunLevel->启动指定级别的服务

## 实模式和保护模式所做的操作

x86体系的处理器刚开始时只有20根地址线，寻址寄存器是16位。可以访问64K的地址空间，如果程序要想访问大于64K的内存，就需要把内存分段，每段64K，用段地址+偏移量的方式来访问，这样使20根地址线全用上，最大的寻址空间就可以到1M字节，这在当时已经是非常大的内存空间了。

实模式将整个物理内存看成分段的区域，程序代码和数据位于不同区域，系统程序和用户程序并没有区别对待，而且每一个指针都是指向实际的物理地址。这样一来，用户程序的一个指针如果指向了系统程序区域或其他用户程序区域，并修改了内容，那么对于这个被修改的系统程序或用户程序，其后果就很可能是灾难性的。再者，随着软件的发展，1M的寻址空间已经远远不能满足实际的需求了。最后，对处理器多任务支持需求也日益紧迫，所有这些都促使新技术的出现。

为了克服实模式下的内存非法访问问题，并满足飞速发展的内存寻址和多任务需求，处理器厂商开发出保护模式。在保护模式中，除了内存寻址空间大大提高；提供了硬件对多任务的支持；物理内存地址也不能直接被程序访问，程序内部的地址(虚拟地址)要由操作系统转化为物理地址去访问，程序对此一无所知。至此，进程(程序的运行态)有了严格的边界，任何其他进程根本没有办法访问不属于自己的物理内存区域，甚至在自己的虚拟地址范围内也不是可以任意访问的，因为有一些虚拟区域已经被放进一些公共系统运行库。这些区域也不能随便修改，若修改就会有出现linux中的段错误，或Windows中的非法内存访问对话框。