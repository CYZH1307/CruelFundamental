# 消息队列如何避免重复消费
- https://juejin.cn/post/6844903807676071949
- https://blog.csdn.net/qianshangding0708/article/details/105336974
- https://www.huaweicloud.com/articles/75655f967f1e97144b8ca639d8f2210c.html
- https://www.cnblogs.com/shengyang17/p/14254515.html#_label3_1_1_1

## 原因
- 网络的不确定性

## 举例
- Kafka 重启后，仍旧从旧的 offset 读取数据

## 解决办法
- 保证服务器端幂等性
- 缓存里用 HashSet 检查
- 对于更新数据库操作，可以将当前余额加到消息里面
