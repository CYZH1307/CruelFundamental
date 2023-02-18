The master database maintains a binary log file called binlog. Each time data insertion, deletion or updates occurs in
the master database, the operation will be stored in the binlog file. The slave database copies the SQL statements from
the master database's binlog file and it stores them in its relay-log. It then executes these SQL statements, after
which the data in the slave database will be the same as those in the master database.