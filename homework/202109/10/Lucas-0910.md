# CLOSE_WAIT 过多的原因及解决办法
- https://blog.huoding.com/2016/01/19/488
- https://juejin.cn/post/6844903734300901390
- https://www.huaweicloud.com/articles/567b137ad606dafafb5c51d5faf217ab.html

## 过程
- Client -- Established -- FIN (FIN_WAIT) -- RCV(FIN_WAIT) -- ACK (TIME_WAIT) -- WAIT (MSLx2) -- CLOSED
- Server -- Established --------- ACK (CLOSE_WAIT) -- FIN (LAST_ACK) --- RCV -- CLOSED
- 客户端给服务器发送 FIN，进入 FIN_WAIT_1
- 服务器收到 FIN，发送 ACK，进入 CLOSE_WAIT
- 客户端收到 ACK，进入 FIN_WAIT_2
- 服务器处理好收尾工作，发送 FIN，进入 LAST_ACK
- 客户端收到 FIN，发送 ACK，进入 TIME_WAIT
- 服务器收到 ACK，关闭链接
- 客户端继续等待两倍MSL，然后关闭

## 原因
- 关闭发起方发送后，无反应，则另一方进入 CLOSE_WAIT
- 程序问题，发起方忘记关闭链接
- 响应太慢，或超时设置太短
- ACCEPT BACKLOG 太大
