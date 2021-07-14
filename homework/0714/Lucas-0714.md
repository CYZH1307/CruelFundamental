# redis为什么效率高，线程，数据结构,网络模型，aio, nio, bio, 为什么这么设计？如何处理高并发
- https://blog.csdn.net/u011059009/article/details/103306632

# IO
- BIO, Blocking IO，同步阻塞，等数据的时候，通信阻塞
- NIO, Non-blocking IO，同步非阻塞，数据没来，轮询
- AIO, Asynchronzed IO，数据来的时候，会收到通知

# Redis 高效
- 单线程，无需考虑并发安全
- 非阻塞，IO多路复用，Reactor模式
- 纯内存操作
- 哈希结构，压缩表，调表
- 自由的时间分离器
