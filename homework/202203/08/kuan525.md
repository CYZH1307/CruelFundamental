#### 请你说一下MySQL引擎和区别

#### MyISAM和InnoDB的特点：

> MyISAM ：默认表类型，它是基于传统的ISAM类型，ISAM是Indexed Sequential Access Method (有索引的顺序访问方法) 的缩写，它是存储记录和文件的标准方法。不是事务安全的，而且不支持外键，如果执行大量的select，insert MyISAM比较适合。
>
> InnoDB ：支持事务安全的引擎，支持外键、行锁、事务是他的最大特点。如果有大量的update和insert，建议使用InnoDB，特别是针对多个并发和QPS较高的情况。注： 在MySQL 5.5之前的版本中，默认的搜索引擎是MyISAM，从MySQL 5.5之后的版本中，默认的搜索引擎变更为InnoDB。

#### MyISAM和InnoDB的区别：

>1. InnoDB支持事务，MyISAM不支持。对于InnoDB每一条SQL语言都默认封装成事务，自动提交，这样会影响速度，所以最好把多条SQL语言放在begin和commit之间，组成一个事务；
>2. InnoDB支持外键，而MyISAM不支持。
>3. InnoDB是聚集索引，使用B+Tree作为索引结构，数据文件是和（主键）索引绑在一起的（表数据文件本身就是按B+Tree组织的一个索引结构），必须要有主键，通过主键索引效率很高。MyISAM是非聚集索引，也是使用B+Tree作为索引结构，索引和数据文件是分离的，索引保存的是数据文件的指针。主键索引和辅助索引是独立的。
>4. InnoDB不保存表的具体行数，执行select count(*) from table时需要全表扫描。而MyISAM用一个变量保存了整个表的行数，执行上述语句时只需要读出该变量即可，速度很快。
>5. Innodb不支持全文索引，而MyISAM支持全文索引，查询效率上MyISAM要高；5.7以后的InnoDB支持全文索引了。
>6. InnoDB支持表、行级锁(默认)，而MyISAM支持表级锁。
>7. InnoDB表必须有主键（用户没有指定的话会自己找或生产一个主键），而Myisam可以没有。
>8. Innodb存储文件有frm、ibd，而Myisam是frm、MYD、MYI。
>
>Innodb：frm是表定义文件，ibd是数据文件。
>
>Myisam：frm是表定义文件，myd是数据文件，myi是索引文件。

目前尚未学习到这部分，此篇借鉴于：[ref](https://www.jianshu.com/p/96b2e57aa3d1)