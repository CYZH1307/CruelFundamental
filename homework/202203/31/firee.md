### C++: 为什么建议优先选用const_iterator 而非iterator？（Effective Modern C++）

iterator在stl中是想要视为指针来使用的，const_iterator 用途等价于用常量指针，即指向const的指针，在大多数场景下我们不需要修改iterator对应的数据(插入，查询，删除)，const限制实际上也避免了我们误操作把数据给改了