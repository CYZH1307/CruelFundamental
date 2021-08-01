# etcd 原理
- https://www.huaweicloud.com/articles/7151d6117992c7a5aa8fc4a10f1dc506.html
- https://www.infoq.cn/article/etcd-interpretation-application-scenario-implement-principle

## 应用场景
- 服务发现
- 消息订阅发布
- 负载均衡
- 分布式系统协调
- 分布式锁和队列
- 集群监控

## 对比 ZooKeeper
- Raft协议 vs Zab/Paxos协议
- 方便运维，社会活跃
- 支持HTTPS/JSON/RPC vs 客户端
- 高性能 16K QPS

## 节点状态
- Leader，Follower，Candidate
- 领导者，随从者，候选者
- 主节点给其他节点发送心跳
- 随从者超时为收到领导者心跳，则编程候选者
- 候选者可能成为新的领导者

## TODO-日志复制
