# 三级封锁协议

MySQL 3个不同级别的封锁协议

一级：

- trasaction在write前，必须对其加exclusive锁，知道transaction结束（Committed or abortted）时释放。read时不需要加锁。未解决 不可重复读 和 dirty read问题。

二级：

- 一级之上，transaction在read前，必须对其加shared锁，读完后释放。解决 dirty read问题，未解决 不可重复读问题

三级：

- 一级智商，transaction在read前，必须对其加shared锁，transaction结束后才释放。解决了 不可重复读的问题。

