#### C++: 为什么建议优先选用const_iterator 而非iterator？（Effective Modern C++）

const_iterator在STL中等价于指向const的指针，被指向的数值时不能修改的，标准做法是应该使用const的迭代器的地方，也就是尽可能的在没有必要修改指针所指向的内容的地方使用const_iterator。