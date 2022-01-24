# binlog和redolog的区别

1.binlog是MySQL server层实现的，所有的引擎都可以用。redolog是innodb引擎专用。  
2.binlog是追加写，新内容会一直往后append。redolog是循环写，给定空间，写超空间后会覆盖旧内容。  
3.binlog是逻辑日志，redolog是物理日志。
    
    a.物理日志记录的是原始记录的变化，具有幂等性。由于其记录了具体变量的地址，用其进行回放可以保证事务一致性。物理日志的回放效率也更高。
    b.逻辑日志字面上易于理解，日志体积更小。
