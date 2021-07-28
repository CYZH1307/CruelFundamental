## MySQL 主从原理

其实就是  master - slave

主要两种方式：异步复制 && 半同步复

不管是哪一种，都是 master 上有两个线程，用户线程和 Dump线程

**master-用户线程用于处理用户 CRUD 的请求，以及 写 binlog**

**master-Dump线程用于 Dump 和 send binlog 给 slave**

Slave-io线程 会收集 master 发送的 binlog，然后 write relay log

### 异步复制：
同上。


### 半同步复制：

半同步复制是在异步复制的基础上， master 用户线程在写 binlog 的时候，会主动通知master的 dump 线程，并且 slave 在 write relay log 之后，会发 ack 给 master。

master 在接收到 slave 的 ack 之后，才会给 client 返回 CRUD 成功。


### 参考：
[mysql 主从同步与半同步](https://www.huaweicloud.com/articles/8f4a530af8cb567d675d0e44973cdb15.html)