### Java: JVM启动参数有哪些，怎么调优，TLAB是什么，阻塞队列对比和选择

#### JVM启动参数，调优思路

https://www.liuzehe.top/archives/2021101801

#### TLAB是什么

TLAB全称是Thread Local Allocation Buffer，即线程本地分配缓存，从名字上看是一个线程专用的内存分配区域，是为了加速对象分配对象而生的。每一个线程都会产生一个TLAB，该线程独享的工作区域，Java虚拟机使用这种TLAB区来避免多线程冲突问题，提高了对象分配的效率

TLAB空间一般不会太大，当大对象无法在TLAB分配时，则会直接分配到堆上

#### 阻塞队列对比和选择

- ArrayBlockingQueue：基于数组结构的阻塞队列，FIFO进行排序
- LinkedBlockQueue：基于链表结构阻塞队列，FIFO进行排序。吞吐量高于ArrayBlockQueue。静态方法Executors.newFixedThreadPool()用了这个队列。
- SynchronousQueue：不存储元素的阻塞队列。每个插入操作需要等到另外一个线程调用移除操作，否则插入操作一直处于阻塞状态。静态工厂方法Executor.newCachedThreadPool使用了这个队列。
- PriorityQueue：具有优先级的无限阻塞队列

```
通常我们可以从以下 5 个角度考虑，来选择合适的阻塞队列：

功能
第 1 个需要考虑的就是功能层面，比如是否需要阻塞队列帮我们排序，如优先级排序、延迟执行等。如果有这个需要，我们就必须选择类似于 PriorityBlockingQueue 之类的有排序能力的阻塞队列。

容量
第 2 个需要考虑的是容量，或者说是否有存储的要求，还是只需要“直接传递”。在考虑这一点的时候，我们知道前面介绍的那几种阻塞队列，有的是容量固定的，如 ArrayBlockingQueue；有的默认是容量无限的，如 LinkedBlockingQueue；而有的里面没有任何容量，如 SynchronousQueue；而对于 DelayQueue 而言，它的容量固定就是 Integer.MAX_VALUE。

所以不同阻塞队列的容量是千差万别的，我们需要根据任务数量来推算出合适的容量，从而去选取合适的 BlockingQueue。

能否扩容
第 3 个需要考虑的是能否扩容。因为有时我们并不能在初始的时候很好的准确估计队列的大小，因为业务可能有高峰期、低谷期。

如果一开始就固定一个容量，可能无法应对所有的情况，也是不合适的，有可能需要动态扩容。如果我们需要动态扩容的话，那么就不能选择 ArrayBlockingQueue ，因为它的容量在创建时就确定了，无法扩容。相反，PriorityBlockingQueue 即使在指定了初始容量之后，后续如果有需要，也可以自动扩容。

所以我们可以根据是否需要扩容来选取合适的队列。

内存结构
第 4 个需要考虑的点就是内存结构。在上一课时我们分析过 ArrayBlockingQueue 的源码，看到了它的内部结构是“数组”的形式。

和它不同的是，LinkedBlockingQueue 的内部是用链表实现的，所以这里就需要我们考虑到，ArrayBlockingQueue 没有链表所需要的“节点”，空间利用率更高。所以如果我们对性能有要求可以从内存的结构角度去考虑这个问题。

性能
第 5 点就是从性能的角度去考虑。比如 LinkedBlockingQueue 由于拥有两把锁，它的操作粒度更细，在并发程度高的时候，相对于只有一把锁的 ArrayBlockingQueue 性能会更好。

另外，SynchronousQueue 性能往往优于其他实现，因为它只需要“直接传递”，而不需要存储的过程。如果我们的场景需要直接传递的话，可以优先考虑 SynchronousQueue。
```

