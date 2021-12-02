# 进程调度算法



1. Batch processing

   - FCFS: first come first server

   - SPN: Shortest Process Next

   - HRRN: Highest Response Ratio Next

     响应比 = （等待时间 + 要求服务时间）/ 要求服务时间

     缺点：每次调度前，要重新计算所有等待进程的响应比

2. Parallel

   - Round Robin：每个进程轮流使用CPU，设立定时器，定时器到时间或者执行阻塞操作，则被踢下线
   - Virtual Round Robin：考虑到I/O heavy 的进程的I/O阻塞情况，创建辅助队列。阻塞接触的进程，进入辅助队列。进行进程调度时，优先选择辅助队列里的进程。
   - 优先级调度：注意调高等待时间长的进程的优先级

   

