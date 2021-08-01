# Linux环境下的线上业务管理
- https://cloud.tencent.com/developer/article/1699111
- https://cloud.tencent.com/developer/article/1699112
- https://cloud.tencent.com/developer/article/1699113
- https://cloud.tencent.com/developer/article/1699114
- https://cloud.tencent.com/developer/article/1699115

## 整体规划
- 应用服务器采用Java环境部署，初始化环境为一到两台
- 数据库采用MySQL主从复制架构，读写分离
- 前段使用Nginx反向代理做负载均衡
- 官网采用静态页面，运行在Nginx，使用FTP服务器进行资源管理
- 所有数据备份到专业存储，数据保留30天
- 所有服务监控用Zabbix
