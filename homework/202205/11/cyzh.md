## 2022/05/11

### C++: STL中 vector、list底层原理是什么？

#### vector

采用倍增思想，在源码中有三个指针，_Myfirst, _Mylast, _Myend, 

_Myfirst, 到 _Myend,代表的是总空间， _Myfirst 到  _Mylast是已经使用的空间，每次push_back(),实际上是移动 _Mylast指针，当 _Mylast

移动到 _Myend 的位置时，vector会申请一个两倍的空间，将数据拷贝到新地址，然后弃掉当前空间