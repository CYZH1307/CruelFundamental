# 哈希索引和聚簇索引有什么区别？

Clustered index is based on the physical order of these records on the disk. One table could have
only one clustered index. It's usually tree-based data structure. It usually supports range query.

Hash index is hash table based, one table could have many hash index. It usually doesn't support range query, 
but spotted query would be much faster. 