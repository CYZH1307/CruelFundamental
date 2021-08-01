# 0731 zookeeper


### ZK 的数据模型

有点像树，ZK的数据存储类似于树结构，有点像文件系统的目录。

节点在 ZK 中叫做 Znode， Znode 中包含 data，ACL， child, stat 等信息


### ZK 集群

ZK 集群是由多个 server 组成的集群，（1 个 leader + 多个 follower）

leader 提供读写服务， 其他 server 提供读服务

只有 leader 能够  execute 更新请求。

### ZK 基本操作

```
create //创建节点

delete //删除节点

exists //判断节点是否存在

getData //获取一个节点的数据

setData //设置一个节点的数据

getChilder //获取当前node下面的所有子节点
```
