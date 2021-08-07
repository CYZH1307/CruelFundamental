# C++ std::atomic 的使用方式以及原理
- https://www.coder.work/article/7133189
- https://www.shuzhiduo.com/A/q4zVx4Q75K/
- https://www.cnblogs.com/5iedu/p/11964414.html
- https://blog.csdn.net/weixin_42157432/article/details/115560285

## 使用
- std::atomic<long> counter(0)
- counter ++
- counter += 2
- counter = counter + 2 不是原子操作
  
## 原理
- 汇编里 xaddl 加了lock这个CPU前缀
- 保证了 load-and-store 步骤的不可分割性
- Bus锁，性能消耗大，其他总线请求操作请求会被阻止
- Cache锁，失败后再用总线锁
