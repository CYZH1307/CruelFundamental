1. std::unique_ptr
- 独占资源所有权 
- 对资源进行管理，离开unique_ptr 对象的作用域时，会自动释放资源。
- 不能拷贝，只能move来传递。

2. std::shared_ptr
- 资源的引用计数为 0 的时候，自动释放资源。
- 一个 shared_ptr 对象的内存开销要比裸指针和无自定义 deleter 的 unique_ptr 对象略大。
- 无自定义 deleter 的 unique_ptr 只需要将裸指针用 RAII 的手法封装好就行，无需保存其它信息，所以它的开销和裸指针是一样的。如果有自定义 deleter，还需要保存 deleter 的信息。
- shared_ptr -需要维护的信息有两部分：指向共享资源的指针; 引用计数等共享资源的控制信息——实现上是维护一个指向控制信息的指针。所以，shared_ptr 对象需要保存两个指针。shared_ptr 的 的 deleter 是保存在控制信息（即第二个指针指向的地方）中，所以，是否有自定义 deleter 不影响 shared_ptr 对象的大小。

3. std::weak_ptr
- std::weak_ptr 要与 std::shared_ptr 一起使用。 一个 std::weak_ptr 对象看做是 std::shared_ptr 对象管理的资源的观察者，它不影响共享资源的生命周期：
- 如果需要使用 weak_ptr 正在观察的资源，可以将 weak_ptr 提升为 shared_ptr。
- 当 shared_ptr 管理的资源被释放时，weak_ptr 会自动变成 nullptr。

enable_shared_from_this
一个类的成员函数如何获得指向自身（this）的 shared_ptr?
- 成员函数获取 this 的 shared_ptr 的正确的做法是继承 std::enable_shared_from_this。
