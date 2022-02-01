# Redis对象类型

五种对象类型：字符串(string)、哈希(Hash)、列表(List)、集合(Set)、有序集合(Zset)。

Redos使用对象存储键和值，每个对象由redisObject结构表示，分为三个属性类型type、编码encoding、底层数据结构指针ptr。

- type类型

Redis键采用哪种对象类型的时候，指的是值采用的对象类型：

REDIS_STRING：字符串对象、REIDS_LIST：列表对象、REDIS_HASH：哈希对象、REDIS_SET：集合对象、REDIS_ZSET有序集合对象。

- ptr指针指向数据结构

  REDIS_ENCODING_INTlong类型的整数REDIS_ENCODING_EMBSTRemstr编码的简单动态字符串REDIS_ENCODING_RAW简单动态字符串REDIS_ENCODING_HT字典REDIS_ENCODING_LINKEDLIST双端链表REDIS_ENCODING_ZIPLIST压缩列表REDIS_ENCODING_INTSET整数集合REDIS_ENCODING_SKIPLIST跳跃表和字典
