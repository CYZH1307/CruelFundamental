保证服务的高可用，要做到

* 服务冗余：把服务部署多份，当某个节点不可用时，切换到其他节点。服务冗余对于无状态的服务是相对容易的。
* 服务备份：有些服务是无法同时存在多个运行时的，比如说：Nginx的反向代理，一些集群的leader节点。这时可以存在一个备份服务，处于随时待命状态。 自动切换：服务冗余之后，当某个节点不可用时，要做到快速切换。

MySQL高可用方案:
* 基于主从复制
* 基于Galera协议
* 基于NDB引擎
* 基于中间件/proxy
* 基于共享存储
* 基于主机高可用