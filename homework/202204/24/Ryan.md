# 乐观锁和悲观锁，让你来写你怎么实现

## 悲观锁

C++中自带mutex和shared_mutex接口，也可以用mutex+atomic的方式实现spinlock。这样互斥锁、读写锁、自旋锁都可以方便实现。这些都是显式的锁，锁住了整个线程，都是悲观锁。

## 乐观锁

### CAS法

实现CAS

```c++

template< class T > bool atomic_compare_exchange_weak( std::atomic* obj,T* expected, T desired ); 
template< class T > bool atomic_compare_exchange_weak( volatile std::atomic* obj,T* expected, T desired );

```


### 版本号法
