# innodb引擎索引实现结构，为什么B+树而不是B树？

innode is using B+ tree to do the query. In B+ tree, all data are in the leaf nodes, it will be much easier to iterate over the nodes than B tree. In that case, range query will be a lot easier. 