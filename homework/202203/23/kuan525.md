#### 讲一讲 std::move 和 std::forward （Effective Modern C++）

有效了解**std::move**和**std::forward**的方法是，了解它们做不了的事情。**std::move**不会移动任何东西，**std::forward**不会转发任何东西，在运行期间，它们什么事情都不会做，不会生成一个字节的可执行代码。

**std::move**和**std::forward**仅仅是表现为转换类型的函数（实际上是模板函数），**std::move**无条件地把参数转换为右值，而**std::forward**在满足条件下才会执行**std::move**的转换。