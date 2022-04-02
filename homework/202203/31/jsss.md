#  C++: 为什么建议优先选用const_iterator 而非iterator？（Effective Modern C++）

`const_iterator`是指向常量的指针, 即无法通过该迭代器修改其指向的值. 在无明确修改需求的前提下, 最好使用`const_iterator`避免修改的发生(如把迭代器传递到其他函数中, 导致被修改了等等).