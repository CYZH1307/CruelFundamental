# TCP 拥塞控制
- https://zhuanlan.zhihu.com/p/59656144
- https://www.youtube.com/watch?v=Xk2gMXuLmdg

## 流量控制
- TCP 协议通过滑动窗口来进行流量控制
- 控制发送方速度
- 让接受者来得及处理

## 拥塞控制
- 防止过多的包发送到网络
- 避免网络负载过大，造成拥塞
- 拥塞状态机，当发送方收到ACK

## 拥塞状态机
- 开放状态，收到ACK，根据拥塞窗口是够小于慢启动阈值，来决定是慢启动还是调整拥塞窗口
- 乱序状态，TODO
- 拥塞窗口减少状态
- 恢复状态
- 丢失状态

## 慢启动算法
- 拥塞窗口初始化大小为1，可以穿一个最大报文长度大小的数据
- 
- 
## 拥塞避免算法

## 拥塞发生算法

## 快速恢复算法

## 术语
-  最大传输单元（Maximum Transmission Unit，MTU）
-  MSS（Maximum Segment Size，最大报文长度
