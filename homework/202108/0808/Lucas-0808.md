# Java 乐观锁与悲观锁
- https://zhuanlan.zhihu.com/p/85912409

## 悲观锁
- 认为所有的人都会修改改数据
- 读写数据前先上锁，用完释放
- 数据库里有，行锁，表锁，读锁，写锁
- 比如 synchronized 和 ReentrantLock 独占锁

## 乐观锁
- 认为别人不会修改数据
- 读取时不会上锁
- 写入时会查看数据是否被修改多
- 实现机制，版本号机制 和 CAS
- TODO: java.util.concurrent.atomic 使用 CAS
