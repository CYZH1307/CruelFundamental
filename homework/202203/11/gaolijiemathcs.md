### Java: 阐述ArrayList、Vector、LinkedList的存储性能和特性

- 底层数据结构：ArrayList和Vector底层为数组，LinkedList为双链表

- 扩容机制：ArrayList底层数组，需要扩容时1.5倍扩容。Vector底层数组，需要扩容2倍扩容。LinkedList为链表，没有扩容概念
- 线程安全：ArrayList和LinkedList多线程环境下不安全；Vector是同步容器，本身自带的方法都加了synchronized，可以保证在多线程环境下，单个操作单独使用的线程安全，但是只是单独操作的安全性，我们无法保证复合操作的线程安全，复合操作需要客户端主动加锁方式来实现，效率不高。例如在涉及多个线程同时执行删除操作，要考虑是否加锁。Vector并发效率低，推荐用并发容器，如果有复合操作，最好用容器自身提供的复合方法。因为Vector加了synchronized效率低，所以不推荐使用。
- 插入和删除：ArrayList和Vector都是数组，插入删除要挪动数组，开销大。LinkedList底层为双链表，操作方便。
- 查找：ArrayList和Vector因为底层是数组查找效率高，LinkedList是链表效率低。
- 内存占用：ArrayList和Vector结尾会预留空间。LinkedList开销是每个存储的链表结点消耗比数组大(前驱和后继和数据)。

