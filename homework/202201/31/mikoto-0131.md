### Redis对象类型
Redis是key/value型数据库，其中每个key和value都是用对象表示的。Redis共有五种对象的类型
1. REDIS_STRING 字符串对象
2. REDIS_LIST 列表对象
3. REDIS_HASH 哈希对象
4. REDIS_SET 集合对象
5. REDIS_ZSET 有序集合对象

为提高效率以及程序执行效率，每种对象底层的数据结构不止一种，据了解，底层数据结构共有八种：
1. REDIS_ENCODING_INT long类型的整数
2. REDIS_ENCODING_EMBSTR embstr编码的简单动态字符串
3. REDIS_ENCODING_RAW 简单动态字符串
4. REDIS_ENCODING_HT 字典
5. REDIS_ENCODING_LINKEDLIST 双端链表
6. REDIS_ENCODING_ZIPLIST 压缩列表
7. REDIS_ENCODING_INTSET 整数集合
8. REIDS_ENCODING_SKIPLIST 跳跃表和字典

- 字符串对象
字符串对象的底层编码可以是int、raw或者是embstr，如果字符串对象可以被解释为整数类型，那么底层就用int类型表示，否则若字符串长度的长度小于39字节，就用embstr对象表示，大于39字节用raw对象表示,embstr对象只需要分配一次内存。
- 列表对象
列表对象的编码可以是ziplist或者是linkedlist，当列表元素个数以及每个元素的值都小于设置值时，使用ziplist，否则使用linkedlist。
- 哈希对象
哈希对象内部有两种实现方式，ziplist和hashtable两种，当字典中元素个数以及每个元素的值都小于设定值时，使用压缩列表实现，否则采用hashtable
- 集合对象
集合对象的内部编码有两种：intset（整数集合）和hashtable（哈希表），当集合中的元素都是整数且集合中的元素数量小于设置值时，采用intset实现，否则采用hashtable实现。
- 有序集合对象
多储存一个score分值，内部采用ziplist或者是skiplist实现，同样地，只有当有序集合元素数量以及每个元素的值都小于设置值时，才使用ziplist否则使用skiplist
