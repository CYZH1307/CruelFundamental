### C++: 为什么建议优先选用const_iterator 而非iterator？（Effective Modern C++）
* 通常迭代器如果只需要遍历但不涉及修改，删除指向的内容，就可以用常量指针const_iterator，更加安全
