## 2022/07/05

### 什么是数据库的四个隔离级别？它们的区别是什么？

#### 读未提交 （Read uncommitted

读到其他事务到还没有提交的数据

#### 读提交（Read Committed）

只能读到已经提交了的内容

#### 可重复读(Repeated Read)

可以重复读取

#### 序列化 Serializable

依次执行，隔离级别最高