# 什么是MVCC Multi-Version Concurrent Control？

多版本并发控制, 是一种并发访问控制方法. 常用于数据库的并发控制中.

以在Innodb中的实现为例：

每一行记录有两个隐藏列. 一列记录最近修改/更新这行的事务版本号. 一列记录这行记录之前的版本地址(Undo Log中). 这样每行记录通过该指针可以形成"版本链".

然后配合ReadView来实现并发访问控制. 其主要方式是判断当前版本对于当前事务"可见性".
