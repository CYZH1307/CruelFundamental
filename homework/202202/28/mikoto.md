## hashmap和treemap的区别，除了红黑树还可以怎么实现treemap？

### hashmap

cpp中，unordered_set, unordered_map是无序关联式容器，可以快速的通过指定键查找对应的值。支持O1的增删改查，顾名思义，内部元素无序。

### treemap

- set、multiset、map以及multimap 是有序关联式容器，不带multi的版本中key值唯一，带multi版本中允许重复值的存在。元素有序，且增删改查复杂度均为$O(logN)$。

### 其它实现？
除了红黑树，还可以用其他平衡树结构实现，例如AVL， treap， splay等
