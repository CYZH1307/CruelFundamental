## MVCC

MVCC: multi-version concurrency control

- 保存数据的历史版本，通过对数据行的多个版本管理来实现数据库的并发控制

- maintain several versions of an object side by side.

- Use write locks to prevent dirty writes

- Reads do not require any locks. Similar to read committed. Record different committed versions of an object.

- principle: *readers never block writers, and writers never block readers*

- The same snapshot is for an entire transaction while read committed use a separate snapshot for each query.
- 一定程度上实现读写并发，只在repeatable read和read comitted两个隔离级别工作。
