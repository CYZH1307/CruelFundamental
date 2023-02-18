五种常见数据类型的实现
- string：SDS(Simple dynamic string), 
- list：双向链表、zipist
- set：字典、ziplist、intset
- zset：跳表、ziplist
- hash：字典

此外，在后续版本中，针对ziplist设计上的不足，新增了quicklist和listpack。

- quicklist 的设计，其实是结合了链表和 ziplist 各自的优势。简单来说，一个 quicklist 就是一个链表，而链表中的每个元素又是一个 ziplist。
- listpack 也叫紧凑列表，它的特点就是用一块连续的内存空间来紧凑地保存数据，同时为了节省内存空间，listpack 列表项使用了多种编码方式，来表示不同长度的数据，这些数据包括整数和字符串。