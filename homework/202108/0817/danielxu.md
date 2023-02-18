# Raft
## consensus 算法特性
* agreement
* validity
* termination

## 角色
* leader: 主节点, 客户端通过主节点来修改集群状态
* follower: 节点起始状态
* candidate: 一段时间没有收到心跳, follower 可以变为candidate, 准备发起选举

## 流程
* 选举
    * 每个节点会有一个 timeout 配置(+随机值), 超过此配置, follower 变为 candidate, 开始发起选举. 
    * 发起选举后, 如果能收到大部分节点的支持, 则此节点成为主节点
    * 每次选举后, 会产生一个新的递增的term 
    * 有可能会出现多个节点同时发起选举, 如果大家收到同样的选票, 就会再进行一轮等待. 随机时间避免了无限的选举
* log replication
    * 主节点收到客户端发来的更新请求, 将改变加到自己的日志中
    * 随后, 会由心跳向集群广播消息 (append message)
    * 当收到了大部分节点的 ack, 就会将此更新commit, 并发送结果给客户端, 下次心跳会让其他follower commit此变更
    * 自此集群状态变为一致
* 脑裂愈合
    * 当出现脑裂后, 各自 partition 独立运行
    * 但是当脑裂恢复后, term 更高的 leader 会占主导地位, 低term leader则退位, 并且 rollback 它的变更.

## resource

* http://thesecretlivesofdata.com/raft/
* database internal