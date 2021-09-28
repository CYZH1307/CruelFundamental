# 讲一讲CAP。为什么会有CP、CA的抉择？
- https://www.ruanyifeng.com/blog/2018/07/cap.html
- https://www.zhihu.com/question/64778723

## 概念
- 1998 Eric Brewer 提出
- Consistency 一致性，写后能读出来
- DDIA 第九章，可线性化
- Availability 可用性，用户的请求必须有反应
- Partition Tolerance 分区容错性，网络分区后仍然能继续工作

## 选择
- CP，银行交易系统
- AP，大型可扩展，最终一致性
