# epoll 的实现原理
- http://luodw.cc/2016/01/24/epoll/
- https://blog.csdn.net/wangfeng2500/article/details/9127421

## 比较
- epoll 是IO多路复用 Multiplexing 的一种方式，在 Linux 内核 2.6 之后引入
- select 模型会一直阻塞在 select 语句，然后收到消息后，再去遍历所有的文件数组
- epoll 会拿到所有的消息，然后直接找到对应的文件
- TODO：为什么 epoll_data 使用 union 结构体

## 使用
- int epoll_create(int maxFiles)
- int epoll_ctl(int epoll ...)，将创建的 socket 加入 epoll 监测，或者移出
- int epoll_wait(int epoll ...) 在给定的 timeout 时间内，如果有消息，就返回用户态进程
- select / poll 会将要监控的 socket 传递给 select / poll 系统调用，将用户态的 socket 列表拷贝到内核
- epoll 在内核初始化的时候，会开辟出自己的内核高速缓存区，以红黑树的方式存在 socket
- 高速缓存区建立在连续的物理内存页，然后用slab层，使用空闲的已经分配好的对象
- 当调用 epoll_wait 的时候，查看整个 socket list 里面有没有数据，只拷贝有数据的句柄给用户态
- LT 模式在句柄的时间没处理完时每次都返回， ET 模式只在第一次返回












