## C++: 为什么vector的插入操作可能会导致迭代器失效？vector的reserve()和resize()方法之间有什么区别？

vector有两个属性 size 和 capacity

size是里面有多少个元素

capacity是vector申请的连续空间的大小

因此当插入导致size大于capacity的时候，则会扩容，向操作系统申请两倍大小的空间再把原来的数据拷贝过去，则导致原来的迭代器失效。



reserver是提取申请空间 改变capacity

resize是改变size的大小