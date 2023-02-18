单机事务要保证ACID, 分布式事务则有一个对应的概念BASE，BASE 代表 Basically Available Soft State Eventual Consistenc。

BA: 基本可用，和CAP中的available，意义一样。

S：Soft State，系统会有一些中间状态，但这种中间态不会影响最终结果，意义和Isolation类似。

E：最终一致性，及经过一段时间后，系统上的数值是正确结果。

BASE也可看成是CAP的延伸。
