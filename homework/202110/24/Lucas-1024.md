# Fundamental

## CH4 多线程

### 4.1 创建多线程
- 实现Runnable接口的run()方法
- 继承Thread，实现run()方法
- Thead也实现了Runable接口，native方法start()是启动现场的唯一方式
- 没有返回值使用Runnable接口，有返回值使用Callable接口
- 提交Callable对象可获得Future对象，get()方法可以得到线程返回的结果对象
- 线程池 ExecutorService pool = Executors.newFixedThreadPool(nTask);
- results.add(poll.submit(new MyCallable(i)));
- pool.shutdown();
- for (Future f : results) {}

## TODO
- 四种线程池
