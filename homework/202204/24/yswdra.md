### 乐观锁和悲观锁，让你来写你怎么实现

------

##### 悲观锁

对于悲观锁，实现思路就是访问数据时先加锁，访问完后释放锁，比较直观的实现可以使用互斥锁的操作。

##### 乐观锁

对于数据加入版本号这一字段，然后使用CAS（对比需要访问的数据，满足条件进行修改，且这些操作绑定为一个原子操作）机制访问数据即可。