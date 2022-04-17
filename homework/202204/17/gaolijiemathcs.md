### 针对你熟悉的编程语言和版本，描述锁的实现

Java中锁有两类：内部锁(synchronized)、显式锁(Lock接口)

底层实现：

synchronized 依赖JVM

(1)修饰对象时使用monitor对象监视器来实现锁机制。

monitor enter 和 monitor exit

(2)修饰方法时，使用ACC_SYNCHRONIZED标识符加锁



显式锁

Lock接口的实现在AQS中，基于CAS操作的等待队列。

AQS底层使用双向链表+锁状态值，构成一个CLH队列。

变量都被transient和volatile修饰。并且通过CAS来操作结点的修改。

Lock可以继承AQS定义各种实现，读写锁(ReadWriteLock)，实现公平锁或者不公平锁，Lock比Condition比wait/notify要方便。



synchronized 通过监视器，底层为操作系统Mutex Lock需要切换用户态和和心态。1.6之后有优化。

Lock底层基于AQS，线程独占，硬件用CPU(CAS)指令，是一种自旋锁。性能较好，避免进入内核态。可以防止死锁。



同步关键字volatile是使用内存屏障



ref:https://juejin.cn/post/6872989550868856840