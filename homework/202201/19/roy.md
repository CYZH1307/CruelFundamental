# 索引的种类

按数据结构分类：

- B+ tree: in disk
- Hash: in memory
- Full-text: in disk

按字段特性分类：

- 主键索引：建立在主键上，一张表只能有一个主键索引。
- 唯一索引：建立在UNIQUE字段上。
- 普通索引：建立在普通字段上。
- 前缀索引：对char, varchar, binary, varbinary 前几个字符/bytes建立索引。
