# MySQL索引结构
- https://blog.codinglabs.org/articles/theory-of-mysql-index.html
- https://www.cnblogs.com/rickiyang/p/13559507.html

## InnoDB引擎
- 面向行存储
- 数据存在磁盘上
- 聚集索引，主键对应的索引
- 第一个非空唯一索引，否则用隐藏的ROW_ID
- 共享序列，无法在高并发下满足唯一性

## 索引结构
- 二叉树
- 哈希表
- B 树
- B+ 树

## B+ 树
- 磁盘读写代价更低
- 查询效率更高
- 查询范围支持好

## MyISAM
- 也用B+ 树
- 索引和数据分离

## 索引类型
- 主键索引，聚集索引，基于主键创建的B+ 树
- 二级索引，各自的B+ 树，叶子结点存储是主键索引的主键值，两次B+ 树查找

## TODO
- 覆盖索引
- 联合索引
- 左前匹配
- 范围查询
- 索引缺点？
