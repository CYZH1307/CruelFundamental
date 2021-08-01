# MySQL 主从原理
- https://zhuanlan.zhihu.com/p/50597960

## 主从复制
- 一个服务器可写，多个从节点可读
- 从写服务器，将数据复制到多个从节点
- 默认采用异步方式

## 应用场景
- 读写分离，高并发
- 实时备份，故障切换
- 高可用
- 架构扩展

## 数据流
- write -> master -> data changes -> binary log
-                              log dump thread +
-                                              |
- read <- SQL thread <- repay log <- IO thread +
