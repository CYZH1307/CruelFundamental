# Java Semaphore 信号量
- https://www.cnblogs.com/nullzx/p/5270233.html

## 互斥锁
- 许可为1，线程独占
- 信号量，许可不唯一，进程共享

## 原理
- java.util.concurrent.Semaphore
- 通过 AQS 管理线程
- 构造参数，permits 表示许可参数
- 线程运行时获得许可，许可数减一
- 获取失败，进程阻塞
- 结束时释放许可，许可数加一
- 创建时，可指定公平性，NonfairSync / FairSync

