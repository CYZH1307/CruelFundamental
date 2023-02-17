## 五种常见的 Redis 数据类型是怎么实现？

1. String通过 int、SDS(simple dynamic string)作为结构存储。int用来存放整型数据，sds存放字节/字符串和浮点型数据。redis3.2分支引入了五种sdshdr类型，目的是为了满足不同长度字符串可以使用不同大小的Header，从而节省内存
2. List类型内部使用双向链表实现。
3. Set的底层数据结构以intset或者值为空的hashtable来存储。
4. Hash类型和Java的 HashMap 更加相似,都是数组+链表的结构.当发生 hash 碰撞时将会把元素追加到链表上.
5. 有序集合类型ZSet内部是以ziplist或者skiplist+hashtable来实现。
