#### InnoDB和MyISAM之间的区别
1. 存储结构: InnoDB的数据是以索引组织表的形式进行存储的; 而MyISAM则是使用堆表进行存储
2. 索引: InnoDB的索引主要可以分为主键索引和二级索引; 而MyISAM中都是二级索引; 此外, InnoDB支持外键
3. 锁: InnoDB中支持行级锁, 表锁; MyISAM只支持表锁
4. 事务: InnoDB支持事务, MyISAM不支持
5. 统计信息: MyISAM以表的形式的存储统计信息, 而InnoDB则是以采样的信息进行统计