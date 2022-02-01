# 简述Redis对象类型
Redis共有五种基础对象：
    1.REDIS_STRING：字符串
    2.REDIS_LIST：列表
    3.REDIS_HASH：哈希
    4.REDIS_SET：无序集合
    5.REDIS_ZSET：有序集合
    
对应的底层实现有8种：
    a.REDIS_ENCODING_INT：long类型的整数
    b.REDIS_ENCODING_EMBSTR：embstr类型的动态字符串
    c.REDIS_ENCODING_RAW：动态字符串
    d.REDIS_ENCODING_HT：字典
    e.REDIS_ENCODING_LINKEDLIST：双端链表
    f.REDIS_ENCODING_ZIPLIST：压缩列表
    g.REDIS_ENCODING_INTSET：整数集合
    h.REDIS_ENCODING_SKIPLIST：跳表和字典
    
其中1.的底层可以是a、b、c。若内容就是整数，用a；若字符串长度小于39bytes，用动态分配更高效的b；其他情况用c。  
2.的底层可以是e、f。若列表不长而且节点对象不大，采用具有内存局部性的f以节省空间和预分配的时间。其他情况用e。  
3.的底层可以是d、f。若哈希表不大而且节点对象不大，采用具有内存局部性的f以节省空间和预分配的时间。其他情况用d。  
4.的底层可以是d、g。若能保证操作的元素都是同一种int类型（例如int16和int32不能混用），则用g。其他情况用d  
5.的底层可以是f、h。若随机读操作较多，则用h。其他情况用f。
