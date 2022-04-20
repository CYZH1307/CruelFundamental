### Linux 读写数据的整个流程，内核状态如何切换

#### 读文件
* 进程调用库函数向内核发起读文件请求；
* 内核通过检查进程的文件描述符定位到虚拟文件系统的已打开文件列表表项；
* 调用该文件可用的系统调用函数read()
* read()函数通过文件表项链接到目录项模块，根据传入的文件路径，在目录项模块中检索，找到该文件的inode；
* 在inode中，通过文件内容偏移量计算出要读取的页；
* 通过inode找到文件对应的address_space；
* 在address_space中访问该文件的页缓存树，查找对应的页缓存结点：
* 如果页缓存命中，那么直接返回文件内容；
* 如果页缓存缺失，那么产生一个页缺失异常，创建一个页缓存页，同时通过inode找到文件该页的磁盘地址，读取相应的页填充该缓存页；重新进行第6步查找页缓存；
* 文件内容读取成功。

#### 写文件
* 前6步和读文件一致，在address_space中查询对应页的页缓存是否存在：
* 如果页缓存命中，直接把文件内容修改更新在页缓存的页中。写文件就结束了。这时候文件修改位于页缓存，并没有写回到磁盘文件中去。
* 如果页缓存缺失，那么产生一个页缺失异常，创建一个页缓存页，同时通过inode找到文件该页的磁盘地址，读取相应的页填充该缓存页。此时缓存页命中，进行第6步。
* 一个页缓存中的页如果被修改，那么会被标记成脏页。脏页需要写回到磁盘中的文件块。有两种方式可以把脏页写回磁盘：
* 手动调用sync()或者fsync()系统调用把脏页写回
* pdflush进程会定时把脏页写回到磁盘
* 同时注意，脏页不能被置换出内存，如果脏页正在被写回，那么会被设置写回标记，这时候该页就被上锁，其他写请求被阻塞直到锁释放。