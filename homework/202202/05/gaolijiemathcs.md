# Redis6.0中的多线程和memcached的多线程区别

## Redis6.0多线程

Redis单线程指获取(socket读)、解析、执行、内容返回(socket写)等都是顺序创新的主线程处理，也即单线程。而其他清理脏数据，释放无用连接，LRU淘汰策略，也是有其他线程处理。

之前提过了Redis这些操作放在同一个主线程：

- 瓶颈不再CPU而在内存和IO
- 多线程会带来线程不安全情况
- 多线程存在线程切换、加锁解锁、死锁性能消耗
- 单线程降低Redis内部实现复杂度
- hash的惰性rehash、lpush等线程不安全命令可以无锁执行。



而Redis6.0引入了多线程。

Redis抽象一套AE事件模型，将IO与时间事件融入，借助多路复用机制(linux中epoll)的回调特性，使得IO读写非阻塞。实现高性能的网络处理能力，加上Redis基于内存处理数据，单线程、但高性能，原因。

IO数据读写仍然阻塞，也是Redis性能瓶颈，特别数据吞吐很大情况下。当socket中有序局，Redis通过系统调用将数据从内核态拷贝到用户态，供Redis解析使用，拷贝的过程是阻塞的，同步IO。数据越大延迟越高，并且都是单线程处理。Redis瓶颈，因此Redis6.0引入"多线程"优化该瓶颈，将主线程的IO读写任务拆分出来给一组独立的线程执行，使得多个socket读写并行化。





Memcache多线程区别：Memcache从IO处理到数据访问多线程实现有区别。**Redis的IO多线程只用于处理网络数据的读写和协议解析，执行命令仍然为单线程。**因为Redis不想因为多线程变复杂，去处理key/lua/事务/LPUSH/LPOP等等的并发问题。



Redis的IO多线程知识在网络数据读写是多线程。

![image-20220205133728795](C:/Users/36987/AppData/Roaming/Typora/typora-user-images/image-20220205133728795.png)

流程：

- 主线程获取socket放入等待队列
- socket分配给IO线程
- 主线程阻塞等待IO线程读取socket完毕
- 主线程以单线程执行命令
- 主线程阻塞，等待IO线程组将数据会写socket完毕
- 解绑定，清空队列。

```
IO线程要么同时在读socket，要么同时在写。不存在同时读同时写。
IO线程只负责读写socket解析命令，不负责执行命令，由主线程串行执行命令。
IO线程组数目可以配置 默认为1.
上面过程无锁，IO线程处理时，主线程会等待全部IO线程完成。
```



## Memcached多线程

Memcache线程模型：

![image-20220205134109940](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220205134109940.png)

Memached服务采用master-worker模式进行，服务端采用socket与客户端同学。主线程、工作线程采用pipe管道同学。主线程采用libevent监听listen、accept读事件，事件响应后将连接信息的数据结构封装，依据算法找对应的工作线程，将连接任务携带连接信息分发，相应的线程利用连接描述符建立与客户端的socket连接，并进行后续的存取数据操作。





## Redis6.0与memcache对比

相同：都用master-worker线程模型

不同点：Memcached执行主逻辑也在worker线程中，模型简单，实现线程隔离，符合线程隔离常规理解。Redis将处理逻辑给master，虽然增加了一些模型复杂度，但是解决了线程并发安全等问题。