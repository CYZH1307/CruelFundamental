# 如何排查线程阻塞
- https://mp.weixin.qq.com/s/7MM4B5GDrheeHGsXt-TlOg

## 问题
- Prometheus 发现 tomcat 忙碌的线程数，线性增长
- jsp
- top -Hp 66182
- pidstat -u -p 66182 1 5
- CPU 利用率不高，线程 ID 经常变动，非死循环或 CPU 空转
- jstack -l 66182
- CompletableFuture.get(15, TimeUnit.SECONDS) 阻塞
- 阻塞队列使用 DiscardPolicy 拒绝策略，任务丢弃而不执行
- CompletableFuture 不会返回结果，所以造成阻塞
- 解决办法： 自动定拒绝策略
- executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
