### 简述epoll 和 poll, select 的区别 

------

首先，poll和select的区别在于，select有最大文件描述符管理数量的限制，而poll没有这个限制，可以无限制的管理。

然后，epoll和上面两者的区别在于，poll、select获取文件描述符的信息需要轮询访问，而epoll不需要，可以直接通过epoll_wait获取活跃的文件描述符列表。

