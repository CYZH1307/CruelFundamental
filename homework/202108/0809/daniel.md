# Reactor

Reactor 一种事件收割,处理解耦合的机制, 结合多线程处理, 可显著提高性能和代码的可维护性.

在此机制中, 有不同的模块:
* reactor: 负责注册回调函数, 和 收割事件, e.g. epoll instance
* handler: e.g socket, timer
* event handler: 回调函数, 当事件被收割后, 回调函数会被触发. 

典型的例子为 数据库中间件: 

* epoll 监听对外端口, 主线程收到建联请求后, 创建连接
* 主线程将 connected socket 和 对应 event handler 注册起来, 并分添加到 worker线程的 epoll interest list 里, worker 线程就会关注此 socket. 
* 同时, worker 线程会一直 epoll wait 进行收割事件
* 当connected socket 有 读/写事件发生, worker 线程会调用对应的处理 handler 来处里
