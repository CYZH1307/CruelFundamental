# MVCC原理
Multi-Version Concurrency Control 保证隔离性的同时避免加锁
* 读未提交
* 读已提交
* 可重复读
* 串行化


## 关键知识点
1. 事务版本号
2. 隐式字段
row_id trx_id roll_pointer
3. undo log
先于表内容修改将数据保存至日志 错误时可以回滚 也可用于MVCC的快照
4. 版本链
5. 快照读 ｜ 当前读
6. read view

m_ids: 活跃的事务
min_limit_id: 最小活跃事务
max_limit_id: 最大活跃事务+1
creator_trx_id: 当前事务id

问题：有了 m_ids 为什么还需要 最小和最大事务id 呢？

## 查询流程
获取 read view
根据 read view 判断数据是否可见
不可见从 undo log 里进行快照读

读已提交 ｜ 不可重复读 ： 在前者中 事务的每次查询都会生成一个新的 read view

