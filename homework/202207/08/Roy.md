实模式和保护模式的区别， 物理地址 = 左移4位的段地址 +偏移地址

在保护模式下，段是通过一些列被称为描述符表的标所定义的，段寄存器存储的是只想这些表的指针，用语定义内存段的标有两种，全局描述表和局部描述表。其中包含所有应用程序都可以使用的基本描述符

在实模式中，段长固定为64KB 而保护模式中段长可变，最大可以达4GB，

保护模式和实模式的根本区别是进程内存受保护与否，实模式将整个物理内存看成分段的的区域，程序代码和数据位于不同区域，系统程序和用户程序没有区别，每一个指针都指向实在的物理地址，这样一来，用户程序的一个指针如果指向了系统程序区域或者其它yoghurt程序区域并改变了值，那么这将可能带来灾难性的后果
