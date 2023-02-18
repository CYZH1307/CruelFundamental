# 索引的种类

按数据结构分类：

- B+ tree: InnoDB, MyISAM, Memory
- Hash: Memory
- Full-text: InnoDB, MyISAM



按字段特性分类：

- 主键索引：建立在主键上，一张表只能有一个主键索引。
- 唯一索引：建立在UNIQUE字段上。
- 普通索引：建立在普通字段上。
- 前缀索引：对char, varchar, binary, varbinary 前几个字符/bytes建立索引。



按字段个数分类：

- 单列索引
- 联合索引



按物理存储分类：

- 聚簇索引
  - 按照主键顺序构建 B+Tree结构。B+Tree 的叶子节点就是行记录，行记录和主键值紧凑地存储在一起。
- 辅助索引
  - 根据索引列构建 B+Tree结构，但在 B+Tree 的叶子节点中只存了索引列和主键的信息。
  - 占用的空间会比聚簇索引小很多，提升查询效率

