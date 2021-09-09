# 分布式全局唯一ID生成器
- https://juejin.cn/post/6844904016141369352

## 需求
- 分库分表后，保证ID唯一
- 全局唯一，整个分布式系统唯一
- ID简短，趋势递增，查询快
- 高可用
- 信息安全，反爬虫

## UUID
- Universally Unique Identifier
- 32个16进制数，四个连字符，五段数字
- 本机生成，全局唯一，高性能
- UUID无序，不可读，字符串存储空间大，查询效率差
- 适合token等场景

## MySQL 主键
- auto_increment，每次ID加1
- ID 数字递增，可读，查询效率高
- 单点问题，数据库不支持高并发

## MySQL 多实例主键自增
- auto_increment + 设置步长 step
- MySQL1 [1,4,7], MySQL2[2,5, 8], MySQL3[3,6,9]
- 解决单点故障问题
- 步长固定后无法扩容，数据库不支持高并发

## Redis - incr
- 单机原子操作，保证唯一递增
- 集群加步长，有序递增，可读性强
- 占用网络带宽，无法扩容？

## Twitter SnowFlake
- 8字节long类型，64个bit
- 第一个bit不可能，41个bit表示毫秒级的时间戳
