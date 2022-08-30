## 2022/08/29

### 什么是Raft算法？

- Raft协议是一种共识算法

- 每个节点可以处于3种状态
  - leader
  - follower
  - candidate
- 所有节点启动时都是follower状态，在一段时间内没有收到leader的投票就会切换到candidate状态，如果收到majority的造成票，则切换到leader状态，永远只有一个leader
- leader将客户端请求封装到一个个log entry里面，将这些log entries复制到所有节点，然后大家按照相同的顺序应用log entry中的客户请求
- 一个log被复制到大多数节点，就是committed，保证不会回滚
- leader一定包含最新的committed log，因此leader只会追加日志，不会删除覆盖日志