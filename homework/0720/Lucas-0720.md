# MySQL Join 底层原理
- https://juejin.cn/post/6844903712670892040
- https://zhuanlan.zhihu.com/p/54275505

## 简单嵌套循环，Simple Nested-Loop Join
- left join

## 索引嵌套循环，Index Nested-Loop Join
- users(id, name)
- levels(user_id, level)
- select * from users u left join levels l
- on u.id = l.user_id
- 非驱动表需要有索引
- 非驱动表主键，性能很高
- 非驱动表非主键，多次回表查询

## 块嵌套索引循环连接，Blocked Nested-Loop Join
- 非驱动表无索引，驱动表所有Join列会缓存到Join Buffer
- 默认join_buffer为256K

## Join
- Index Nested-Loop Join
- or Blocked Nested-Loop Join

## Left Join
- TODO

## Tips
- 小标作为驱动表
- 为匹配条件增加索引？
- 增加Join Buffer大小
- 减少非必要字段查询
