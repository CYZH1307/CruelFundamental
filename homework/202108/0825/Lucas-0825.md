# Java 自旋锁
- https://tech.meituan.com/2018/11/15/java-lock.html
- https://learnku.com/articles/49689
- https://www.jianshu.com/p/824b2e4f1eed
- https://blog.csdn.net/fuyuwei2015/article/details/83387536
- https://juejin.cn/post/6844903838349000717

## 原因
- 锁定资源的时间是极短的
- 后面的线程不挂起，而是不断顶着前面的线程是否已经释放锁
- JDK 1.4.2
