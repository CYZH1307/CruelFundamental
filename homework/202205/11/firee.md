### C++: STL中 vector、list底层原理是什么？
vector:
基于数组
内存动态增长，每次不够用了增长一定倍数重新申请内存
list:
基于双向链表
每次push_back都会重新分配一块内存并且进行链接
