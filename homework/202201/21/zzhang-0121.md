事务具有哪些特性?

ACID

A: Atomicity 原子性

- 一个transaction中，操作要么全部成功 comitted 要么全部失败 abortted。

C: Consistency 一致性

- 事务使数据库从一个一致性状态转换到另外一个一致性状态
- You have certain statements about your data (*invariants*) that must always be true. If a transaction starts with a database that is valid according to these invariants, and any writes during the transaction preserve the validity, then you can be sure that the invariants are always satisfied.

I: Isolation 隔离性

- 多用户并发访问或写入数据库时，要提供不同程度的隔离避免race condition

  - *serializability*: each transaction can pretend that it is the only transaction running on the entire database. Rarely use.

  - snapshot isolation: weaker guarantee than serializability

D: Durability 持久性

- Once a transaction has committed successfully, any data it has written will not be forgotten, even if there is a hardware fault or the database crashes.
- Perfect durability does not exist

