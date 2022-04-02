C++: 为什么建议优先选用const_iterator 而非iterator？（Effective Modern C++）
const_iterator 相当于常量指针，我们不允许通过指针去修改其指向的值，而很多时候我们仅仅是利用迭代器遍历数据结构，无需修改，因此用const_iterator更安全
