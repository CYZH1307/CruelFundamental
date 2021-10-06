# 计算机总共有哪几种底层机制来保证并发的正确
- https://dannashen.github.io/2019/05/22/%E5%B9%B6%E5%8F%91%E6%9C%BA%E5%88%B6%E5%8F%8A%E5%85%B6%E5%BA%95%E5%B1%82%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86/

## 上下文切换
- 单核处理器也支持多线程
- CPU给每个线程分配CPU时间片
- 时间片非常短
- CPU不停切换线程
- 无锁并发编程，数据ID取模，不同线程处理不同段的数据
- 比较设置算法
- 尽量少用线程
- TODO：使用协程

## TODO-volatile

## TODO-synchronized
