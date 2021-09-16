# 每日一题

## 0901
- 你根据什么来决定你的项目用sql还是nosql database?

## 0902
- 实现两线程交替打印

## 0903
- 聊聊缓存击穿、缓存穿透、缓存雪奔
- https://mp.weixin.qq.com/s/RSvsxTJApxbw9PAlxMKXww

## 0904
- 布隆过滤器是什么？有什么用？
- https://mp.weixin.qq.com/s/BfAHWsnQkre4iwOuT-DVsA

## 0905
- ThreadLocal真的会造成内存泄露？
- https://mp.weixin.qq.com/s/pMu7IGUDFwV0drf-Vwl4Sw

## 0906
- RocketMQ,为什么要主动拉取消息而不使用事件监听方式？

## 0907
- 讲讲Java中的垃圾回收器是怎么工作的

## 0908
- 请你设计一个算法，用来压缩一段URL？

## 0909
- 谈一谈，id全局唯一且自增，如何实现？

## 0910
- 如果服务器的很多个端口处于close wait是什么情况 还问了epoll触发机制有哪些

## 0911
- 描述一种语言内建最小堆的实现

## 0912
- close wait 就是服务器端还有数据给客户端发 但是服务器端不会再发送数据给客户端？ epoll就 et和lt两种是吗

## 0913
- Linux 文件系统相关，inode结构，软硬连接，以及文件描述符是什么，怎么去修改，最多允许多少？

## 0914
- 一道SQL语句题目，要求我建立合适的索引，并解释为什么（实际上考察B+树结构、最左前缀等知识）

## 0915
- Linux 读写数据的整个流程，内核状态切换；Mmap(内存映射)和sendFile函数零拷贝什么原理？

## 0916
- 为什么gRPC使用HTTP2传输？
- https://github.com/refinedcoding/CruelFundamental/blob/main/blogs/gRPC%E7%B3%BB%E5%88%97(%E4%B8%89)%20%E5%A6%82%E4%BD%95%E5%80%9F%E5%8A%A9HTTP2%E5%AE%9E%E7%8E%B0%E4%BC%A0%E8%BE%93%20-%20%E7%9F%A5%E4%B9%8E.pdf

## 0917
- 简述RedLock多节点分布式锁算法

## 0918
- 简述如何设计延时队列
- https://github.com/refinedcoding/CruelFundamental/blob/main/blogs/%E4%B8%80%E5%8F%A3%E6%B0%94%E8%AF%B4%E5%87%BA%206%E7%A7%8D%20%E5%BB%B6%E6%97%B6%E9%98%9F%E5%88%97%E7%9A%84%E5%AE%9E%E7%8E%B0%E6%96%B9%E6%B3%95%EF%BC%8C%E9%9D%A2%E8%AF%95%E5%AE%98%E4%B9%9F%E5%BE%97%E6%9C%8D.pdf

## 0919
- Kafka如何保证消息有序不丢？
- https://github.com/refinedcoding/CruelFundamental/blob/main/blogs/%E5%88%A8%E6%A0%B9%E9%97%AE%E5%BA%95%EF%BC%8CKafka%E6%B6%88%E6%81%AF%E4%B8%AD%E9%97%B4%E4%BB%B6%E5%88%B0%E5%BA%95%E4%BC%9A%E4%B8%8D%E4%BC%9A%E4%B8%A2%E6%B6%88%E6%81%AF.pdf

## 0920
- Redis 为什么这么快
- https://github.com/refinedcoding/CruelFundamental/blob/main/blogs/%E5%A4%A7%E5%8E%82%E7%BB%8F%E5%85%B8%E9%9D%A2%E8%AF%95%E9%A2%98%EF%BC%9ARedis%E4%B8%BA%E4%BB%80%E4%B9%88%E8%BF%99%E4%B9%88%E5%BF%AB%EF%BC%9F.pdf

## 0921
- K8S 为何舍弃 Docker

## 0922
- Kafka 为何舍弃 ZooKeeper

## Pending
- 父进程死掉子进程会怎么样？子进程挂掉父进程呢？知道僵尸进程吗，Linux会怎么处理？
- 数据库隔离级别，Mysql怎么去实现的
- Linux说一说fork这个函数，返回什么；父子进程之间资源是共享的的吗？父子进程之间怎么用匿名管道通信？
- 一道SQL语句题目，好像是一个update语句和一个select for update，然后判断是否有问题（实际上就是考察gap lock），建议看极客时间有个比较出名的Mysql36讲。
- Linux是内存管理是怎么样的？虚存是什么，虚拟地址->物理地址的整个流程是什么？
- 一道SQL语句题目（具体忘了），有多个SQL语句模拟ID分发器的业务，判断在默认隔离级别下会不会存在并发上的问题（实际上考察的是innodb的多版本并发控制机制）

- RocketMQ在面试中那些常见问题及答案
- https://mp.weixin.qq.com/s/vxHzNs23-P4BvYvyME6_nQ
- 《大厂面试》之JVM篇21问与答
- https://mp.weixin.qq.com/s/FT6FHAyebXlqHVbT479c3w


## Reference
- https://mp.weixin.qq.com/s/jzII5_p5KcXxmbLRH1GYRw
- https://www.nowcoder.com/discuss/688906



## Done

## 0621
- Please describe what happens when you click on a URL in your browser.

## 0622
- 面向对象有哪些特征，并用你熟悉的语言描述如何实现。

## 0623
- 请介绍一下TCP三次握手原理及过程。

## 0624
- 用熟悉的实现支持多线程的单例模式，并列举优缺点

## 0625
- 请描述HTTPS连接和通信的过程。

## 0626
- 请介绍一个编程语言中的内存管理模型，对象生命周期，或者有哪些高效安全的管理方式

## 0627
- 比较B树和B+树，并列出应用场景

## 0628
- 针对你熟悉的编程语言和版本，描述HashMap如何实现的。

## 0629
- 悲观锁和乐观锁

## 0630
- 针对你熟悉的编程语言和版本，描述其基本数据类型和字节数

## 0701
- 进程和线程，区别，哪个效率高，为什么

## 0702
- 针对你熟悉的编程语言，描述字符串类型的内存管理机制，比如String，StringBuffer, StringBuilder区别

## 0703
- 死锁的条件，如何解决

## 0704
- 针对你熟悉的编程语言和版本，描述其泛型

## 0705
- 请问Redis 相比 Memcached 有哪些优势?

## 0706
- mysqI索引结构,特点，为什么使用这个

## 0707
- 聚集索引和非聚集索引

## 0708
- 垃圾回收机制GC, cms, G1,垃圾回收的算法

## 0709
- 子类和父类的实例变量和方法有什么区别

## 0710
- 重载和覆盖区别，返回值类型不同，可以重载吗，为什么

## 0711
- mysql底层原理，为什么效率高，主键能不能太大，为什么

## 0712
- linux查询tcp连接处理CLOSE_ WAIT的状态的数目

## 0713
- RabbitMQ, kafka, RocketMQ, ActiveMQ, 以及其他消息中间件

## 0714
- redis为什么效率高，线程，数据结构,网络模型，aio, nio, bio, 为什么这么设计？如何处理高并发

## 0715
- 数据仓库和数据湖的区别

## 0716
- 分布系统的设计，分布式系统CAP，分布式系统的模型

## 0717
- linux环境下的线上业务管理有没有，如何管理

## 0718
- redis的集合有没有限制，限制是多少

## 0719
- redis的1w条的插入和更新有什么区别

## 0720
- MySQL join的底层原理是什么，有哪几种(不是左右连接这种)

## 0721
- 针对你熟悉的编程语言和版本，描述锁的实现

## 0722
- Linux命令查询一个文件的行数

## 0723
- Linux命令查询一个文件内出现重复最多的单词

## 0724
- 针对你熟悉的一门语言，描述数组排序的实现


## 0725
- 请描述协程和线程，进程的区别

## 0726
- Spring IOC AOP

## 0727
- MySQL主从原理

## 0728
- Spring中用到的设计模式

## 0729
- Kafka的架构

## 0730
- SpringMVC的启动流程

## 0731
- Zookeeper的原理

## 0801
- etcd原理

## 0802
- Java ReentrantLock 原理与使用
- Python RLock 原理与使用

## 0803
用自己熟悉的语言创建一个线程池。

## 0804
Semaphore 的实现原理

## 0805
并发队列：什么是阻塞队列，什么是非阻塞队列，请分别举例子说明。最好附带上使用场景。

## 0806
- 介绍一下 Java 中的 ConcurrentLinkedQueue，ArrayBlockingQueue，LinkedBlockingQueue

## 0807
- Java: Atomic 的使用场景以及原理
- C++: std::atomic 的使用方式以及原理。

## 0808
- Java: 简述乐观锁，悲观锁，公平/非公平锁
- C++: 简述 lock_guard, 和 unique_lock

## 0809
- Reactor模式的优势

## 0810
- 消息队列如何避免重复消费

## 0811
- epoll的实现

## 0812
- C/C++/Java/GoLang如何分配内存

## 0813
- Linux mmap原理和优势

## 0814
- Linux Shared Memory原理和优势

## 0815
- Redis集群如何保持数据一致

## 0816
- Redis分布式锁原理

## 0817
- 描述一种分布式共识算法

## 0818
- Docker 原理

## 0819
- 如何解决TCP拥堵

## 0820
- K8S 原理

## 0821
- vistor pattern 介绍

## 0822
- listener pattern 介绍

## 0823
- 事务隔离的几个级别

## 0824
- 逃逸分析

## 0825
- 自旋锁

## 0826
- Linux下动态链接和静态链接的优缺点；

## 0827
- C++11“完美转发”的作用，举例说明；

## 0828
- Redis的一致性算法

## 0829
- 实现C++ std::next_permutation；

## 0830
- 线程joinable/detach区别和实现

## 0831
- 策略模式
