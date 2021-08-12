# Epoll 实现原理

* 数据结构: 红黑树 + ready list
    * 红黑树里存储 想要监听的 fd + 事件类型 (通过 epoll_ctl 来添加删除)
    * 在insert 到红黑树的时候, 会注册事件就绪时对应的回调函数 
    * ready list 里存已经就绪的 IO
* 事件就绪
    * 当被监听的 fd 的对应事件发生后, 其注册的回调函数被调用, 继而 ep_poll_callback 会将此 fd(对应的结构体)加入到 ready list 中
* epoll_wait 事件收割
    * 通过 ep_poll() 来对 ready list 进行扫描, 将就绪的事件及其 data 拷贝到用户态内存中