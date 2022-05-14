C++: STL中 map, set, multiset, multimap底层原理是什么？

红黑树

set和multiset会根据特定的排序准则自动将元素排序。

set中元素不允许重复，multiset可以重复。

map和multimap将key和value组成的pair作为元素，根据key的排序准则自动将元素排序（因为红黑树也是二叉搜索树，所以map默认是按key排序的），map中元素的key不允许重复，multimap可以重复。map和set的增删改查速度为都是logn，是比较高效的。

插入的时候由于插入的是结点，类似链表，因此不会导致迭代器失效。



set/multiset是以rb_tree为底层机构，因此有元素自动排序的特性。
排序的依据是key，而set/multiset的value和key合一：value就是key，其中value由key和data组成。
set/multiset提供遍历操作和迭代器，按正常规则（++iter）遍历，便能获得排序状态（sorted）
我们无法使用set/multiset的迭代器改变元素值（因为key有严谨的排序规则）。
set/multiset的迭代器是其底部的RB Tree的const-iterator，就是为了禁止用户对元素赋值
set元素的key必须对一无二，因此其insert()用的是rb_tree的insert_unique().
multiset元素的key可以重复，因此其insert()用的是rb_tree的insert_equal().
