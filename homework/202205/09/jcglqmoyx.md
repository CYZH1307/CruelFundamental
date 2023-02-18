C++ STL

STL中的主要容器有vector, array, list, queue, deque, set, mutilset, map, multimap, unordered_set, unordered_map,
unordered_multimap, unordered_multiset 等

vector, array 支持随机索引访问。 list为双向链表结构，支持高效地插入操作，不支持随机索引访问。 queue为FIFO队列， deque为双端队列。 set为有序集合，不支持重复元素。map为有序哈希映射，不允许有重复元素。
multiset 和 multimap分别为set和map的支持重复元素的版本。 unordered_map, unordered_set分别为无序集合与无序哈希表，不支持重复元素。unordered_multimap,
unordered_multiset分别为unordered_map和unordered_set的支持重复元素的版本。