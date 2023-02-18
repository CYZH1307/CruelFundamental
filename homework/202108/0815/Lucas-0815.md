# Redis 数据一致性
- https://whetherlove.github.io/2018/10/09/Redis%E9%9B%86%E7%BE%A4-%E9%9B%86%E7%BE%A4%E4%B8%80%E8%87%B4%E6%80%A7%E9%97%AE%E9%A2%98/

## 异步复制
- Redis 集群保证了数据的最终一致性，而不是强一致性
- 客户端向主节点写入一条命令
- 主节点向客户端回复命令的状态
- 主节点将写入操作复制给其他从节点

## 网络分区
- 集群有主节点 A/B/C，还有对应的从节点 A1/B1/C1，客户端 X
- 分区1，A/A1,B1,C/C1
- 分区2，B 和客户端 X
- 客户端仍然可以写入主节点B
- 在节点超时时间内，客户端仍然可以写入原来的主节点
- 一旦超过时间，分区1中B1会选举为新的主节点

## 分布式锁
- TODO
- https://whetherlove.github.io/2018/10/09/Redis%E9%9B%86%E7%BE%A4-%E9%9B%86%E7%BE%A4%E4%B8%80%E8%87%B4%E6%80%A7%E9%97%AE%E9%A2%98/

## 并发竞争
- TODO
- https://whetherlove.github.io/2018/10/09/Redis%E9%9B%86%E7%BE%A4-%E9%9B%86%E7%BE%A4%E4%B8%80%E8%87%B4%E6%80%A7%E9%97%AE%E9%A2%98/
