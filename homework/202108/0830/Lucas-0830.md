# 线程之 Join / Detach
- https://zhiqiang.org/coding/std-thread.html
- https://www.cnblogs.com/fnlingnzb-learner/p/6959285.html

## 线程同步
- 等待 join
- 互斥锁 mutex
- 条件变量 condition variable
- joinable 线程可用 pthread_detach 显示分离，分离后不能再合并，操作不可逆

## 线程声明周期
- 线程默认状态为 joinable
- 如果线程结束没被 join，不能释放资源
- 调用者应该使用 pthread_join 来等待线程运行结束，获得线程退出代码，回收其资源
- 如果线程没结束，调用者会阻塞，等待线程
- 如果希望调用者不被阻塞，则用 pthread_detach

