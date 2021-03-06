### C++: 什么是智能指针？智能指针有什么用？分为哪几种？各自有什么特点？

智能指针是一个RAII类模型，用于动态分配内存，其设计思想是将基本类型指针封装为（模板）类对象指针，并在离开作用域时调用析构函数，使用delete删除指针所指向的内存空间。

智能指针的作用是，能够处理内存泄漏问题和空悬指针问题。

分为auto_ptr、unique_ptr、shared_ptr和weak_ptr四种，各自的特点：

● 对于auto_ptr，实现独占式拥有的概念，同一时间只能有一个智能指针可以指向该对象；但auto_ptr在C++11中被摒弃，其主要问题在于：

-对象所有权的转移，比如在函数传参过程中，对象所有权不会返还，从而存在潜在的内存崩溃问题；

-不能指向数组，也不能作为STL容器的成员。

● 对于unique_ptr，实现独占式拥有的概念，同一时间只能有一个智能指针可以指向该对象，因为无法进行拷贝构造和拷贝赋值，但是可以进行移动构造和移动赋值；

● 对于shared_ptr，实现共享式拥有的概念，即多个智能指针可以指向相同的对象，该对象及相关资源会在其所指对象不再使用之后，自动释放与对象相关的资源；

● 对于weak_ptr，解决shared_ptr相互引用时，两个指针的引用计数永远不会下降为0，从而导致死锁问题。而weak_ptr是对对象的一种弱引用，可以绑定到shared_ptr，但不会增加对象的引用计数。
