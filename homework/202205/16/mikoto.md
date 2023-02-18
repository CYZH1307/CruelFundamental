### C++: stl中unordered_map和unordered_set的底层原理是什么？

stl中的实现是拉链法的哈希表，取模因子有一系列预先设置好的数，因此其实unordered_set和unordered_map在声明的时候就占用了一部分空间，若放在循环内部反复声明的话问题很大。 当容器内部数据量到达一定数量后，会扩大取模因子进行重哈希。
