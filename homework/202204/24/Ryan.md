# 乐观锁和悲观锁，让你来写你怎么实现

## 悲观锁

C++中自带mutex和shared_mutex接口，也可以用mutex+atomic的方式实现spinlock。这样互斥锁、读写锁、自旋锁都可以方便实现。这些都是显式的锁，锁住了整个线程，都是悲观锁。

## 乐观锁

### CAS法

实现CAS，当obj的值等于expected时，将desired写入obj

```c++

template< class T > bool atomic_compare_exchange_weak( std::atomic* obj, T* expected, T desired ); 
template< class T > bool atomic_compare_exchange_weak( volatile std::atomic* obj, T* expected, T desired );

```

用它来控制数据结构的写操作（如队列的出队入队），即可在原子时间内实现写操作，不会被不同线程的操作干扰。但无法处理ABA问题。

### 版本号法

一般是在数据表中加上一个数据版本号version字段，表示数据被修改的次数，当数据被修改时version值会加1。（在数据库事务中，这个方法叫MVCC，version叫transaction id。）

当线程A要更新数据值时，在读取数据的同时也会读取version值，在提交更新时，若“刚才读取到的version值”等于“当前数据库中的version值”时才更新，否则重试更新操作，直到更新成功。
