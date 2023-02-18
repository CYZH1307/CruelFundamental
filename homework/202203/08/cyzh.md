## 2022/03/08

### 请你说一下MySQL引擎和区别

### Innodb和MyISAM的区别

Innodb和MyISAM是MySQL的两个常用的存储引擎，

#### 区别：

1. Innodb支持事务处理，外键等，而MyISAM不支持
2. Innodb 5.7及以后支持全文索引，MyISAM一直支持
3. Innobd 必须有主键，MyISAM可以没有
4. Innodb 支持表锁，行锁（默认），MyISAM支持表锁
5. Innodb 存储文件为：frm，ibd；MyISAM为：frm，MYD，MYI