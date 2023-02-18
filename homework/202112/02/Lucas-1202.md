# 进程调度算法
- https://www.cnblogs.com/xiaolincoding/p/13631224.html
- CPU 调度算法
- 先来先服务, FIFS
- 最短作业优先, Priority Queue, 操作系统怎么知道一个CPU作业的时间长短？
- 高响应比优先，优先权=（现有等待时间+要求服务时间）/ 要求服务时间
- 时间片轮转，时间片设为20ms到50ms，时间片结束执行下个作业
- 最高优先级
- 多级反馈队列
