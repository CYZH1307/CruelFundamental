# 最左前缀

检索数据，会从联合索引的最左边匹配

联合索引也是b+ tree

联合索引会用最左端的字段来构建b+ tree

同时遇到范围匹配会停止

## 例子

select * from xxx where a = 1 and b = 2 and c = 3 =》 abc, acb, bac ... 都可以

select * from xxx where a > 1 and b = 2 =》 (b,a)

select * from xxx where a > 1 and b = 2 and c > 3 => (b, a) / (b, c)

select * from xxx where a = 1 ORDER BY b => a

select * from xxx where a IN (1,2,3) and b > 1 =》 a