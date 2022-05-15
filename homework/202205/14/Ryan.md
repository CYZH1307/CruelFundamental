# C++: STL中 map, set, multiset, multimap底层原理是什么？

## map和set
底层用的红黑树

## multiset和multimap

底层用的hash+list，删除一个value时要注意不能通过删key的方式来达成，而是删iterator。
