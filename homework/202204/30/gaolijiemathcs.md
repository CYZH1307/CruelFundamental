### 数据库主主、主从、主备的区别

主主：双主节点，均可进行读写，主库之间需要双向同步。

主从：一主多从，主库写，从库读，从主库向从库单向同步。

主备：主库读写，备库用于备份，主库单向同步备份到备库。