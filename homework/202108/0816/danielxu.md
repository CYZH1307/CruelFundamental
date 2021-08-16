# distributed lock

## why do we need distributed lock
* 正确性: 类似多线程 data race, 只不过现在的对象是多个节点
* 提升效率: 多个节点, 只需要单个节点需要进行计算任务

## redlock algorithm
* N个 redis master
* 客户端先获取当前时间, 然后去逐个上锁 每个 redis 实例
    * 如果上锁时间超过了 自动释放时间 (防止自己死锁), 就尝试下一个
    * 如果拿到了大多数节点的锁, 即加锁成功
    * 剩余锁时间为初始锁时间 -  获取锁所用时间
* 锁获取失败, 需要解锁所有实例

## 关于 redlock 的讨论

http://antirez.com/news/101
https://martin.kleppmann.com/2016/02/08/how-to-do-distributed-locking.html