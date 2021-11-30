# 线程和协程的区别
- https://www.html.cn/qa/other/20371.html
- https://zhuanlan.zhihu.com/p/59178345
- https://www.cnblogs.com/liang1101/p/7285955.html

# 协程
- 可以暂停执行的函数
- 拥有自己的寄存器上下文和调用栈
- 和线程不同，协程是异步操作
- 协程区别于Lambda，每次重新调用的时候，恢复上次的状态，有点像thread local变量
- 线程之上的一种抽象

## C++
- asio in C++ 20
- async_call in C++ 23

## Golang
- go func()
- 传入或用类的锁
