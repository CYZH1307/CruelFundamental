### Java：创建线程的几种方式？

1、继承Thread类创建线程

- 重写run()方法执行体，start启动线程。

2、Runnable接口创建线程类

- 定义Runnable接口，重写run()方法。start启动线程。不返回值。
- Thread类为Runnable实现类的实例。

3、Callable和Future创建线程

- Callable接口实现call()方法作为线程执行体。并且能够抛出运行时异常。伴随返回值。
- Future是一个接口，FutureTask是Future的实现类。FutureTask类包装Callable，封装Callable的返回值。配合线程池使用，异步任务。
emergence ref:gaolijiemathcs.md
