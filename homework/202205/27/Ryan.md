# C++: shared_ptr是如何实现的？

 * 使用了堆上的共享内存和template来存储指针本身ptr和引用计数cnt。
 * 每个shared_ptr至少拥有3个成员：指向ptr的T * 指针、指向cnt的int * 指针、维护线程安全的互斥锁（锁住cnt）。
 * 初始化时会在堆上申请ptr和cnt的空间并把cnt置为1。
 * 每有新的shared_ptr实例指向它时，cnt++；每有shared_ptr实例被销毁时，cnt--。若cnt被减到0，则释放ptr。
