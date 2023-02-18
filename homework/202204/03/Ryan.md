# 简述epoll 和 poll, select 的区别

### select与poll

两者都会对候选socket进行轮询，因为socket信息属于设备信息，两者都需要切换到内核态才可以查询，这是最大性能瓶颈。poll的改进在于没有最大连接数的限制，因为其存储socket信息是基于链表的

### epoll

    不再轮询socket，而是通过回调函数的形式，让活跃的socket的fd来调用主线程的callback，这样可以大大减少用户态内核态的切换次数，idle connections不再会被虚空切到内核态，从而提高了性能。  
    在几乎所有链接都处于非闲置状态的场景下，epoll没有太大的优势。但实际网络场景往往相反，会存在大量闲置的连接，这样epoll的优势就能体现出来。
