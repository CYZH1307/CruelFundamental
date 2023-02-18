### Java: 讲讲线程池原理，参数，使用场景，参数设置分析。

#### 线程池原理

线程池提交一个任务之后，线程池处理任务流程：

```
(1)线程池判断核心线程池里面的线程是不是都在执行任务，如果不是，则创建一个新的工作线程来执行任务，如果有核心线程池里面的线程都在执行任务，则进入下一个流程。
(2)线程池判断工作队列是不是满，如果工作队列没有满，则将提交的任务存储在工作队列里面，如果工作队列满了，则进入下一个流程。
(3)线程池判断线程池的线程是否都处在工作状态，如果没有，则创建一个新的工作线程来执行任务，如果已经满了，则交给饱和策略来处理任务。
```

执行ThreadPoolExecutor执行execute方法分下面4种情况：

```
(1)如果当前运行的线程少于corePoolSize，则创建新的线程来执行任务(注意执行这一步需要获取全局锁)
(2)如果运行的线程>=corePoolSize, 则将线程加入BlockingQueue
(3)如果无法将任务加入BlockQueue(队列已经满了)，则创建新的线程来处理任务(需要获取全局锁)
(4)如果创建新线程将会会让当前运行线程超过maximumPoolSize, 任务将会被拒绝，并且调用RejectedExecutionHandler.rejectedExecution()方法。
```

使用上述步骤设计思路，为了在执行execute()方法的时候尽可能避免获取全局锁。

在ThreadPoolExecutor完成预热(核心线程数大于corePoolSize)之后，几乎所有execute()方法都是执行步骤2。步骤2不用创建全局锁。

源码分析：

```java
public void execute(Runnable command) {
	if(command == null) throw new NullPointerException();
    // 如果线程数量小于基本线程数量，则创建线程并且执行当前任务
    int c = ctl.get();
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))
             return;
        c = ctl.get();
    }
    // 如果线程数大于等于基本线程数，或者线程创建失败，则将当前任务放到工作队列当中。
	if (isRunning(c) && workQueue.offer(command)) {
    	int recheck = ctl.get();
        if (! isRunning(recheck) && remove(command))
        	reject(command);
        else if (workerCountOf(recheck) == 0)
        	addWorker(null, false);
	}
    // 如果线程池不处于运行状态 或者 任务无法放入队列，并且当前线程数量小于最大允许的线程数量
    // 则试图创建一个线程执行任务 如果无法创建则抛出拒绝
    else if (!addWorker(command, false))
            reject(command);
}
```

这里有很多Worker，实际上Worker就是工作线程。

工作线程：线程池创建线程的时候，会将线程封装为工作线程Worker，Worker在执行完任务以后，还会循环获取工作队列里面的任务来执行，可以从Worker的run方法中发现

```java
class ThreadPoolExecutor {
    class Worker extends AbstractQueuedSynchronizer
            implements Runnable {
        public void run() {
                runWorker(this);
        }
    }


	// 实际执行的方法
	final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        w.unlock(); // allow interrupts
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) {
                w.lock();
                // If pool is stopping, ensure thread is interrupted;
                // if not, ensure thread is not interrupted.  This
                // requires a recheck in second case to deal with
                // shutdownNow race while clearing interrupt
                if ((runStateAtLeast(ctl.get(), STOP) ||
                     (Thread.interrupted() &&
                      runStateAtLeast(ctl.get(), STOP))) &&
                    !wt.isInterrupted())
                    wt.interrupt();
                try {
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        task.run();
                    } catch (RuntimeException x) {
                        thrown = x; throw x;
                    } catch (Error x) {
                        thrown = x; throw x;
                    } catch (Throwable x) {
                        thrown = x; throw new Error(x);
                    } finally {
                        afterExecute(task, thrown);
                    }
                } finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }
}
```

所以实际上，线程池中的线程执行任务会有两种情况：

- (1)execute()方法中创建一个线程，会让这个线程执行当前任务
- (2)这个线程执行完图中1的任务以后 ，当前线程会反复从BlockQueue获取任务来执行。

向线程池提交方法有两种execute()与submit()

- execute() 用于提交不需要返回的任务。无法判断是否执行成功。
- submit() 用于提交会有返回值的方法。返回一个future类型对象，来判断是否执行成功。future.get()可以获取返回值，get()会一直阻塞到当前线程执行任务完成。 也有get(timeout, unit)阻塞当前线程一段时间返回 这种有可能任务没执行完。

关闭线程池：shutdown 或者shutdownNow。原理遍历所有线程池中工作线程并且逐个调用线程interrupt方法中断线程。 shoutDownNow为设置线程池状态为STOP 尝试停止所有正在执行的线程，返回等待执行任务列表。shutdown为设置SHOUTDOWN，中断所有没有正在执行任务的线程。

只要调用这两个关闭方法中的一个isShutdown就会为true 当所有的任务都关闭 则isTerminaed会显示为true。任务需要执行完用shutdown 不用执行完用shutdownNow。

```
ExecutorService threadPool = Executors.newSingleThreadExecutor();  // 单个线程的线程池
ExecutorService threadPool = Executors.newFixedThreadPool(5);   // 创建一个固定的线程池大小
ExecutorService threadPool = Executors.newCachedThreadPool();    // 弹性伸缩线程池
```



#### 参数(七大参数 四种拒绝策略)

ThreadPoolExecutor

```
new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, millisecond, runnableTaskQueue, handler);
```

7个参数含义：

- （1）【corePoolSize 基本线程池大小】：当提交一个任务到线程池的时候，线程池会创建一个线程来执行任务，即使其他空闲的基本线程能够执行新任务，也会创建线程，等到需要执行的任务数大于线程池基本大小的时候就不再创建。 如果调用线程池prestartAllCoreThread() 则一开始就会提前创建并且启动所有基本线程。
- （2）【runnableTaskQueue 任务队列】：用于保存等待执行的任务的阻塞队列，可以选择以下几个阻塞队列：
  - ArrayBlockingQueue：基于数组结构的阻塞队列，FIFO进行排序
  - LinkedBlockQueue：基于链表结构阻塞队列，FIFO进行排序。吞吐量高于ArrayBlockQueue。静态方法Executors.newFixedThreadPool()用了这个队列。
  - SynchronousQueue：不存储元素的阻塞队列。每个插入操作需要等到另外一个线程调用移除操作，否则插入操作一直处于阻塞状态。静态工厂方法Executor.newCachedThreadPool使用了这个队列。
  - PriorityQueue：具有优先级的无线阻塞队列
- maximumPoolSize（线程池最大数量）：线程池允许创建的最大线程数。如果队列满了，而且创建的线程数小于最大线程数，那么线程池会继续创建新的线程执行任务。
- ThreadFactory：用于设置创建线程的工厂，一般不改。
- RejectedExecutionHandler（拒绝策略/饱和策略)：当前队列满了，线程池处于饱和状态，采取策略处理提交的新任务。默认为AbortPolicy。
  - AbortPolicy：直接抛出异常
  - CallerRunsPolicy：只用调用者所在线程处理
  - DiscardOldestPolicy：丢弃队列里面最近的一个任务，并且执行当前任务
  - DiscardPolicy：不处理，丢弃掉。
  - 也可以自定义策略。自己实现RejectedExecutionHandler
- keepAliveTime（线程活动保持时间）：线程池的工作线程空闲以后，保持存活的时间。任务很多并且执行的时间比较短，可以调大时间，提高线程利用率。
- TimeUnit：线程活动保持时间单位。



四种拒绝策略：

```
(1)AbortPolicy:直接抛出异常
(2)CallerRunsPolicy: 让调用者所在的线程执行 哪来的回哪去
(3)DiscardOldestPolicy: 丢弃队列中最近的一个任务 并且执行当前任务 不会抛出异常 会尝试和最早的竞争
(4)DiscardPolicy: 不处理 丢弃掉 不会抛出异常
```



#### 使用场景

```
(1)降低资源消耗：通过重复利用已经创建的线程降低线程创建和销毁造成的消耗
(2)提高响应速度：当任务到达的时候，任务可以不需要等到线程创建和销毁造成的消耗
(3)提高线程的可管理性：线程为稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统稳定性，使用线程池可以统一分配线程、调优和监控。
```



#### 参数设置分析

CPU密集型：	计算机几核就定义为几，保持cpu的效率最高，获取当前电脑线程个数。

```
 Runtime.getRuntime().availableProcessors() //获取当前计算机的线程个数
```

```java
int coreNum = Runtime.getRuntime().availableProcessors();
        // 自定义一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors(), // CPU密集型
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()    // 线程池满了 阻塞队列满了 还有人进来 就不处理这个人 抛出异常
        );
```

IO密集型：15个大型任务，IO十分占用资源。判断程序中十分耗IO的线程，只要大于这个耗IO的线程数就可以。一般可以设置为2*Ncpu.

混合型：可以拆分的话就分为CPU密集型和IO密集型任务，如只要两个任务执行的时间相差不是太大 分解后执行的吞吐量高于串行执行的吞吐量。相差太大没必要分解。

依赖数据库连接池的任务，线程提交SQL要等待，等待时间越长CPU空闲时间越长，那么线程数应该设置越大，有利于利用CPU。



建议使用有界队列。提高系统稳定性。例如SQL挂了，线程一致阻塞在线程池，堆积在线程池里面，如果万一是无界队列，线程池会越来越大撑满内存，导致系统不可用。



线程池的监控：

- taskCount：线程池需要执行的任务数量
- completedTaskCount：线程池在运行过程中已经完成的任务数量，小于或者等于taskCount.
- largestPoolSize：线程池曾经创建过的最大线程数量。可以看线程池是否满过，如果大于线程池最大大小则满过。
- getPoolSize：线程池的线程数量，如果线程池不销毁，则线程池里面的线程不会自动销毁。这个大小只会增大不会减小。
- getActiveCount：获取活动的线程数。

