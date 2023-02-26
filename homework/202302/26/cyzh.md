## 02月26日

Redis 如何实现延迟队列？

- 加一个时间属性score
- 使用zrangebyscore
  - zrangebyscore会返回有序集合指定分数区间的成员列表
  - 基本语法：`zrangebyscore key min max [WITHSCORES][LIMIT offset count]`
- 如果有满足条件的元素，就先删除元素，然后处理

