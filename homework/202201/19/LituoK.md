#### 索引的种类有哪些?
按照索引的存储结构区分
B树索引
哈希索引
按照索引列区分
1. 主键索引: 对主键建立的索引, 每个表有且仅有一个, 是一种非空唯一索引
2. 唯一索引: 唯一索引对应的列不能存在相同的值, 允许有空置
3. 普通索引: 非主键所建立的索引
4. 组合索引: 对多个列建立的索引