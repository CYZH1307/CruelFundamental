Redis 部署方式

1. 主从复制模式
   - 包含一个master和多个slave
   - slave启动后，向master发送`SYNC`命令，master接收到`SYNC`命令后通过bgsave保存快照，并使用缓冲区这段时间内执行的写命令
   - master将保存的snapshot发送给slave，并继续记录执行的写命令
   - slave接收到快照文件后，加载snapshot，载入数据
   - master向slave发送缓冲区的写命令，slave接收命令并执行，完成复制初始化
   - 此后master每次执行一个写命令都会同步发送给slave，保持master与slave之间数据的一致性
2. Sentinel模式
   - 基于主从复制模式，只是引入了哨兵来监控与自动处理故障
     - 监控master、slave是否正常运行
     - 当master出现故障时，能自动将一个slave转换为master
     - 多个哨兵可以监控同一个Redis，哨兵之间也会自动监控
3. Cluster模式
   - 分布式存储，无中心结构，每台节点存储不同的内容
   - 所有的redis节点彼此互联(PING-PONG机制)，节点的fail是通过集群中超过半数的节点检测失效时才生效。客户端连接集群中任何一个可用节点即可。
