# MVCC原理

### 背景补充

#### ACID性质

##### Atomicity
Atomicity guarantees that it would be a total & complete success or failure . AKA “all or nothing”

##### Consistency
All data are valid and under all constraints, cascades, and triggers.

##### Isolation
Transaction will not affect each other.

##### Durability
Transactions need to be stored permanently, even in case of system crash...

### 常见问题
脏读、不可重复读、幻读。

### 四大隔离级别
读未提交，读已提交，可重复读，串行化（Serializable）

### MVCC
MVCC最大的优势：读不加锁，读写不冲突

MVCC只在REPEATABLE READ和READ COMMITTED两个隔离级别下工作。
