Linux消息队列和命名管道的区别
消息队列跟命名管道有不少的相同之处，通过与命名管道一样，消息队列进行通信的进程可以是不相关的进程，同时它们都是通过发送和接收的方式来传递数据的。在命名管道中，发送数据用write，接收数据用read，则在消息队列中，发送数据用msgsnd，接收数据用msgrcv。而且它们对每个数据都有一个最大长度的限制。

与命名管道相比，消息队列的优势在于:

消息队列也可以独立于发送和接收进程而存在，从而消除了在同步命名管道的打开和关闭时可能产生的困难。 同时通过发送消息还可以避免命名管道的同步和阻塞问题，不需要由进程自己来提供同步方法。 接收程序可以通过消息类型有选择地接收数据，而不是像命名管道中那样，只能默认地接收