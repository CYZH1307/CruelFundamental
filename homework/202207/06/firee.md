### 什么是共享锁，排他锁，意向锁？

##### **共享锁：**

又称为 **[读锁](https://www.zhihu.com/search?q=%E8%AF%BB%E9%94%81&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra=%7B%22sourceType%22%3A%22article%22%2C%22sourceId%22%3A%22380937367%22%7D)（S锁）** ，当有多个事务时，多个事务对于同一数据可以共享一个锁，都能访问到数据，但是其他事务只能读不能写。

##### **排他锁：**

又称为 **写锁（X锁）** ，当有多个事务时，排他锁不能与其他锁并存，一个事务获取了一行数据的排他锁，其他事务就不能再获取该行的其他锁，包括共享锁与排他锁。  但是获取排他锁的事务是可以对数据进行读取和修改的。


##### [意向锁](https://www.zhihu.com/search?q=%E6%84%8F%E5%90%91%E9%94%81&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra=%7B%22sourceType%22%3A%22article%22%2C%22sourceId%22%3A%22380937367%22%7D)：

InnoDB所用的表级锁，其设计目的主要是为了在一个事务中揭示下一步将要被请求的锁的类型。

InnoDB中的两个表锁：

意向共享锁（IS）：表示事务准备给数据行加入共享锁，也就是说一个数据行加共享锁前必须先取得该表的IS锁

意向排他锁（IX）：类似上面，表示事务准备给数据行加入排他锁，说明事务在一个数据行加排他锁前必须先取得该表的IX锁。

意向锁是InnoDB自动加的，不需要用户干预。


参考：https://zhuanlan.zhihu.com/p/380937367
