# MySQL索引优化方法
- https://www.runoob.com/w3cnote/mysql-index.html

## 检测
- 默认索引算法B树？
- 查询检测: explain select birthday from users where birthday < '1990/2/2';
- select type: simple
- type: range
- possible_keys: birthday
- key: birthday
- key_len: 4
- ref: const
- rows: 1
- Extra: Using where; Using index

## 索引类型
- Unique 索引值唯一
- Index 普通索引
- Primary Key 主索引
- FullText 全文索引，只有Myisam支持，或第三方插件 CoreSeek，XunSearch

## TODO
- 创建索引的技巧
- 组合索引
- 前缀索引
- 不走索引
- 索引弊端
