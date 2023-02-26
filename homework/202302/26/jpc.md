# Redis 如何实现延迟队列？
使用有序集合（ZSet）的方式来实现延迟消息队列的，ZSet 有一个 Score 属性可以用来存储延迟执行的时间。
使用 zadd score1 value1 命令就可以一直往内存中生产消息。
再利用 zrangebysocre 查询符合条件的所有待处理的任务， 通过循环执行队列任务即可。
