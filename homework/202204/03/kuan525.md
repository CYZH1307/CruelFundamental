### 简述epoll 和 poll, select 的区别 （和2021/12/18重复）

select:它仅仅知道了，有 I/O 事件发生了，却并不知道是哪那几个流(可能有一个，多个， 甚至全部)，我们只能无差别轮询所有流，找出能读出数据，或者写入数据的流，对他们进行 操作。所以select 具有 O(n)的无差别轮询复杂度，同时处理的流越多，无差别轮询时间就 越长。它是基于数组来存储的，它有最大连接数的限制。

poll:poll 本质上和 select 没有区别，它将用户传入的数组拷贝到内核空间，然后查询每个 fd 对应的设备状态， 但是它没有最大连接数的限制，原因是它是基于链表来存储的.

epoll:epoll 可以理解为 event poll，不同于忙轮询和无差别轮询，epoll 会把哪个流发生了 怎样的 I/O 事件通知我们。所以我们说 epoll 实际上是事件驱动(每个事件关联上 fd)的， 此时我们对这些流的操作都是有意义的。(复杂度降低到了 O(1))，通过红黑树和双链表数 据结构，并结合回调机制，造就了 epoll 的高效，epoll_create()，epoll_ctl()和 epoll_wait()系统调用。

select和poll都只提供了一个函数:select或者poll函数。
而epoll提供了三个函数，epoll_create,epoll_ctl和epoll_wait，epoll_create是创建一个epoll句柄；epoll_ctl是注册要监听的事件类型；epoll_wait则是等待事件的产生。

### io 多路复用原理，在 redis 和 mysql 的实现
常见I/O模型
同步阻塞IO（Blocking IO）：即传统IO模型
同步非阻塞IO（Non-blocking IO）：默认常见的socket都是阻塞的，非阻塞IO要求socket被设置成NONBLOCK
IO多路复用（IO Multiplexing）：即经典的Reactor设计模式，也被称为异步阻塞IO，Java中的selector和linux中的epoll都是这种模型
异步IO（Asychronous IO）：即Proactor设计模式，也被称为异步非阻塞IO

**为什么 Redis 中要使用 I/O 多路复用这种技术呢？**
首先，Redis 是跑在单线程中的，所有的操作都是按照顺序线性执行的，但是由于读写操作等待用户输入或输出都是阻塞的，所以 I/O 操作在一般情况下往往不能直接返回，这会导致某一文件的 I/O 阻塞导致整个进程无法对其它客户提供服务，而 I/O 多路复用就是为了解决这个问题而出现的。

I/O 多路复用
虽然还有很多其它的 I/O 模型，但是在这里都不会具体介绍。
阻塞式的 I/O 模型并不能满足这里的需求，我们需要一种效率更高的 I/O 模型来支撑 Redis 的多个客户（redis-cli），这里涉及的就是 I/O 多路复用模型了：
在 I/O 多路复用模型中，最重要的函数调用就是 select，该方法的能够同时监控多个文件描述符的可读可写情况，当其中的某些文件描述符可读或者可写时，select 方法就会返回可读以及可写的文件描述符个数。