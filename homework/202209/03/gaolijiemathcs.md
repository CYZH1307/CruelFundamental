### 什么是 Linux 中的文件描述符？dup 系统调用的作用是什么？dup 和 dup2 的区别是什么？



1、什么是 Linux 中的文件描述符？

 我们知道在Linux系统中一切皆可以看成是文件，文件又可分为：普通文件、目录文件、链接文件和设备文件。在操作这些所谓的文件的时候，我们每操作一次就找一次名字，这会耗费大量的时间和效率。所以Linux中规定每一个文件对应一个索引，这样要操作文件的时候，我们直接找到索引就可以对其进行操作了。

文件描述符（file descriptor）就是内核为了高效管理这些已经被打开的文件所创建的索引，其是一个非负整数（通常是小整数），用于指代被打开的文件，所有执行I/O操作的系统调用都通过文件描述符来实现。同时还规定系统刚刚启动的时候，0是标准输入，1是标准输出，2是标准错误。这意味着如果此时去打开一个新的文件，它的文件描述符会是3，再打开一个文件文件描述符就是4......

Linux内核对所有打开的文件有一个文件描述符表格，里面存储了每个文件描述符作为索引与一个打开文件相对应的关系，简单理解就是下图这样一个数组，文件描述符（索引）就是文件描述符表这个数组的下标，数组的内容就是指向一个个打开的文件的指针。

ref:https://blog.csdn.net/yushuaigee/article/details/107883964



2、dup 系统调用的作用是什么？dup 和 dup2 的区别是什么？

由于利用管道实现进程间通信，是通过创建两个文件描述符，但是描述符的初始化是通过随机的，就是从可用的文件描述符中取出，并将可用的文件描述符与 file对象相关联，如果我们需要将管道的两头与其他的流相关时，就需要重定向操作，重定向fd[0]和fd[1]的file，下面是关于实现重定向的函 数dup和dup2的解释：

系统调用dup和dup2能够复制文件描述符。dup返回新的文件文件描述符（没有用的文件描述符最小的编号）。dup2可以让用户指定返回的文件描述符的值，如果需要，则首先接近newfd的值，他通常用来重新打开或者重定向一个文件描述符。

ref:https://www.cnblogs.com/pengdonglin137/p/3286627.html