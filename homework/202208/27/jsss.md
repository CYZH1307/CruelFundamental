# 什么是CAP原理，什么是FLP不可能定理？

## CAP

C：Consistency

即一致性，访问所有的节点得到的数据应该是一样的。注意，这里的一致性指的是强一致性，也就是数据更新完，访问任何节点看到的数据完全一致，要和弱一致性，最终一致性区分开来。

A：Availability

即可用性，所有的节点都保持高可用性。注意，这里的高可用还包括不能出现延迟，比如如果节点B由于等待数据同步而阻塞请求，那么节点B就不满足高可用性。

也就是说，任何没有发生故障的服务必须在有限的时间内返回合理的结果集。

P：Partiton tolerance

即分区容忍性，这里的分区是指网络意义上的分区。由于网络是不可靠的，所有节点之间很可能出现无法通讯的情况，在节点不能通信时，要保证系统可以继续正常服务。

- [原文链接](https://blog.csdn.net/weixin_56219549/article/details/119513609)

## FLP不可能定理

在网络可靠，但允许节点失效（即便只有一个）的最小化异步模型系统中，不存在一个可以解决一致性问题的确定性共识算法

- [原文链接](https://zhuanlan.zhihu.com/p/384511433)