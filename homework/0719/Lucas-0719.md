# Redis的1w条的插入和更新有什么区别
- https://www.cnblogs.com/ivictor/p/5446503.html

## 插入大量数据
- 非阻塞IO如何支持并发？
- Redis 2.6支持管道模式
- 生成支持Redis协议的文本
- cat data.txt | redis-cli --pipe
- 批量操作发到服务器端执行
- 没有更多数据的时候，发送ECHO命令，并附带20个随机字符
- 收到回复，匹配成功就退出

## 更新大量数据
- 有可能有重复
- 在客户端合并，然后再发往服务器端
