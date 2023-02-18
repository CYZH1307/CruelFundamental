### Java: ConcurrentHashmap 了解过吗，讲讲CHM结构，线程安全保证，加锁实现细节，CHM7和8的区别，实现原理区别

#### ConcurrentHashmap结构

##### 1.7 数据结构：

Segment数组+HashMap。

对某个Segment加分段锁的时候，不会影响其他其他Segment的读写，每个Segment里面是由HashEntry组成的数组，HashEntry可以构成链表，降低锁的竞争。

Segment加锁的时候，不会影响其他Segment的读写。

##### 1.8数据结构：

取消Segment数组，采用CAS+synchronized保证并发安全。 数组+链表+红黑树。

给每个数组的头结点加锁，锁粒度降低。



#### 实现原理

##### 1.7 实现原理

（1）Segment继承了ReentrantLock，每当一个线程访问一个Segment，不会影响其他Segment。

（2）put操作：先定位Segment，再进行put操作。过程中，先尝试获取Segment，如果获取失败有竞争，则利用 `scanAndLockForPut()` 自旋获取锁。重试次数达到`MAX_SCAN_RETRIES` 则改为阻塞锁获取，保证能获取成功。

（3）get操作：key通过hash定位Segment，再hash定位到具体元素。HashEntry有volatile修饰，保证内存可见性。get方法高效因为不需要加锁。但是查询的时候还是需要遍历链表，效率低。



##### 1.8 实现原理

（1）抛弃分段锁，`CAS+synchronized`保证并发，同时也引入了红黑树。数组+链表+红黑树

（2）put操作：key计算hashcode，判断是否初始化，当前key定位出Node，如果为空则可以写入数据，CAS尝试写入，失败则自旋保证成功。如果当前位置hashcode==MOVED==-1则需要扩容。不满足则使用synchronized写入，如果链表数量长度大于YREEIFY_THRESHOLD则需要转为红黑树。

（3）get操作：hashcode寻址，如果不存在返回，如果存在链表/红黑树，则按照链表/红黑树方式遍历。



