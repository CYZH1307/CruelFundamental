## 数据库主从复制的原理是什么？

首先主从复制是什么？简单来说是让一台MySQL服务器去复制另一台MySQL的数据，使两个服务器的数据保持一致。

通过复制功能，构建一个或者多个从库，可以提高数据库的**高可用性**、**可扩展性**，同时实现**负载均衡**。当主库发生故障时，可以快速的切到其某一个从库，并将该从库提升为主库，因为数据都一样，所以不会影响系统的运行；当MySQL服务器需要扛住更多的读请求时，可以把读请求的流量分流到各个从库上去，写请求则转发给主库，形成读写分离的架构，来提供更好的读扩展和请求的负载均衡。

MySQL的主从复制支持两种方式：

- 基于**行**
- 基于**语句**

其本质都是基于主库的**binlog**来实现的，主库记录binlog，然后从库将binlog在自己的服务器上重放，从而保证了主、从的数据一致性。



####  binlog

MySQL中日志分为两个维度，一个是**MySQL服务器**的，一个是底层**存储引擎**的。而上文提到的binlog就是属于MySQL服务器的日志，binlog也叫二进制日志，记录了所有对MySQL所做的更改。

基于行、语句的复制方式跟binlog的存储方式有关系。 binlog有三种存储格式，分别是Statement、Row和Mixed。

- **Statement** 基于语句，只记录对数据做了修改的SQL语句，能够有效的减少binlog的数据量，提高读取、基于binlog重放的性能
- **Row** 只记录被修改的行，所以Row记录的binlog日志量一般来说会比Statement格式要多。基于Row的binlog日志非常完整、清晰，记录了所有数据的变动，但是缺点是可能会非常多，例如一条`update`语句，有可能是所有的数据都有修改；再例如`alter table`之类的，修改了某个字段，同样的每条记录都有改动。
- **Mixed** Statement和Row的结合，怎么个结合法呢。例如像`update`或者`alter table`之类的语句修改，采用Statement格式。其余的对数据的修改例如`update`和`delete`采用Row格式进行记录。

为什么会有这么多方式呢？因为Statement只会记录SQL语句，但是并不能保证所有情况下这些语句在从库上能够正确的被重放出来。因为可能顺序不对。

MySQL什么时候会记录binlog呢？是在事务提交的时候，并不是按照语句的执行顺序来记录，当记录完binlog之后，就会通知底层的存储引擎提交事务，所以有可能因为语句顺序错误导致语句出错。



#### 复制的核心步骤

我们假设主库已经开启了binlog，并正常的记录binlog。

首先从库启动**I/O线程**，跟主库建立客户端连接。

主库启动**binlog dump**线程，读取主库上的binlog event发送给从库的I/O线程，I/O线程获取到binlog event之后将其写入到自己的Relay Log中。

然后从库启动**SQL线程**，将Relay中的数据进行重放，完成从库的数据更新。

总结来说，主库上只会有一个线程，而从库上则会有两个线程。



结构：

#### 一主多从

当然你也可以把它当成**一主一从**。

这是最简单的模型，特别适合少量写、大量读的情况。读请求被分到了各个从库上，有效的帮主库分散了压力，能够提升**读并发**。当然，你也可以只是把从库当成一个灾备库，除了**主从复制**之外，没有其他任何的请求和数据传输。

甚至你可以把其中一个备库作为你的预发环境的数据库，当然，这说到底还是直接动了生产环境的数据库，是一种过于理想的用途，因为这还涉及到生产环境数据库的数据敏感性。不是所有人都能够接触到的，需要有完善的权限机制。

#### 级联复制

![MySQL级联复制](https://segmentfault.com/img/remote/1460000038967224)

级联复制的好处在于很大程度上减轻了主库的压力，主库只需要关心与其有直接复制关系的从库，剩下的复制则交给从库即可。相反，由于是这种层层嵌套的关系，如果在较上层出现了错误，会影响到挂在该服务器下的所有子库，这些错误的影响效果被放大了。



### 复制方式

#### 异步复制

首先就是异步，这也是MySQL默认的方式。在异步复制下，主库不会主动的向从库发送消息，而是等待从库的I/O线程建立连接，然后主库创建`binlog dump`线程，把binlog event发送给I/O线程，流程如下图。

主库在执行完自己的事务、记录完binlog之后就会直接返回，不会与客户端确认任何结果。然后后续由binlog dump线程异步的读取binlog，然后发送给从库。**处理请求**和**主从复制**是两个完全异步化的过程。

#### 同步复制

同步模式则是，主库执行一个事务，那么主库必须等待所有的从库全部执行完事务返回commit之后才能给客户端返回成功

值得注意的是，主库会直接提交事务，而不是等待所有从库返回之后再提交。MySQL只是延迟了对客户端的返回，并没有延后事务的提交。

同步模式用脚趾头想知道性能会大打折扣，它把客户端的请求和主从复制耦合在了一起，如果有某个从库复制线程执行的慢，那么对客户端的响应也会慢很多。

#### 半同步复制

半同步相对于同步的区别在于，同步需要等待所有的从库commit，而半同步只需要一个从库commit就可以返回了。如果超过默认的时间仍然没有从库commit，就会切换为异步模式再提交。客户端也不会一直去等待了。因为即使后面主库宕机了，也能至少保证有一个从库节点是可以用的，此外还减少了同步时的等待时间。