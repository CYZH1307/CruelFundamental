### 分布式事务如何解决？TCC了解？

分布式事务：指业务计算流程的一系列操作，拆分为不同的服务以后，分布在不同的结点上，这种分布式的场景下，保证数据操作流程的正确执行，因此提出了分布式事务。



分布式事务解决方案：

- 2pc（两段式提交）
- 3pc（三段式提交）
- TCC（Try、Confirme、Cancel）
- 最大努力通知
- XA
- 本地消息表
- 半消息/最终一致性
- 事务消息



TCC方案

- Try阶段：执行业务流程。
- Confirm阶段：Try没问题，则执行最终的业务状态设置（应该需要对业务表字段增加状态确认字段，即Try业务流程预留的确认字段）。
- Cancel阶段：如果上面两个过程有出现失败，则需要进行回滚操作。