# Java 线程池
- https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html
- https://www.liaoxuefeng.com/wiki/1252599548343744/1306581130018849
- https://crossoverjie.top/2018/07/29/java-senior/ThreadPool/
- https://www.cnblogs.com/pcheng/p/13540619.html

## 三种方法
- JDK 1.5 引入
- 无限，Executors.newCachedThreadPool()
- 固定大小，Executor.newFixedThreadPool()
- 单个线程，Executors.newSingleThreadExecutor()
- 初始化都会调用 ThreadPoolExecutor()
- 使用时调用 threadPool.execute(new Job())

## 内部实现
- 基本大小，corePoolSize
- 最大数量，maximumPoolSize
- 存活时间，keepAliveTime
- 阻塞队列，workQueue
- 饱和策略，handler

## 工作流程
- 获取线程池状态
- 数量小于基本大小，创建新的线程
- 开始运行后，放入阻塞队列
- 双重检查，线程结束，从队列移除
- 创建失败，执行策略
