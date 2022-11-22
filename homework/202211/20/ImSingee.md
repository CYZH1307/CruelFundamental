- 红黑树是二叉查找树的一种，在实现了高效查找的同时实现了高效插入删除，所有操作都是严格 O(logn) 的（不会出现极端情况下退化为链表的情况）。  
- 红黑树是近似平衡的，每次平衡代价严格为 O(logn)，故相对于高度平衡的 AVL 树其插入性能好很多  
-  
- 实现：https://oi-wiki.org/ds/rbtree/  