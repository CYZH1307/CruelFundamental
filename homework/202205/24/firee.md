### 进程间通信方式
匿名管道：
可以实现父子进程间的通信，在mit6.s081中有简易的实现

管道：
可以实现进程间的通信，管道有文件实体

消息队列：
消息队列是保存在内核中的消息链表，在发送数据时，会分成一个一个独立的数据单元，也就是消息体（数据块），消息体是用户自定义的数据类型，消息的发送方和接收方要约定好消息体的数据类型，所以每个消息体都是固定大小的存储块，不像管道是无格式的字节流数据。如果进程从消息队列中读取了消息体，内核就会把这个消息体删除。

共享内存：
可以通过页表的神奇操作，把进程虚拟空间中的某段地址映射到同一段物理地址，实现进程间的通信，操作的时候内核是无感的

信号量：
管理通信时的信息

socket：
逻辑上实现了进程间的通信(tcp/udp等协议)