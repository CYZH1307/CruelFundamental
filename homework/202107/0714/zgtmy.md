# redis为什么效率高，线程，数据结构,网络模型，aio, nio, bio, 为什么这么设计？如何处理高并发
1.redis使用了epoll，不阻塞，  
2.epoll使用了mmap使应用程序和内核可以共享部分内存，不需要再用户空间和内核空间拷贝文件描述符。  
3.mmap在共享区域维护了一个红黑树，里面存的是fd，当有事件到达可读可写事件，会把文件描述符加到共享区域的链表里，应用程序只需要从链表中读有事件  
的文件描述符     

### bio是同步阻塞,nio是同步非阻塞的，aio是异步非阻塞  

aio目前Linux内核还不支持，只支持nio，linux支持的nio分三种select poll epoll  
select和poll是把文件描述符交给内核，让内核通过轮训的方式来找到可以读写的文件描述符  
epoll是通过把文件描述符放在内核和应用程序共享的内存里，放到这个内存里的红黑树，当有时间到达就把文件描述符放到这个内存的链表中，应用程序  
就可以直接从共享内存中读取有事件到达的文件描述符，然后进行读写操作。  
