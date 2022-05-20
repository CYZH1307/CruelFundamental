### Java: CountDownLatch和CyclicBarrier是什么，有什么区别?

#### CountDownLatch

用于实现一个线程等待其他线程的特定操作的结束。

等待线程执行CountDownLatch.await()，通知执行线程执行CountDownLatch.countDown()。

为避免等待线程永远处于暂停状态无法被唤醒，通常CountDownLatch.countDown()需要放在finally块当中。

一个CountDownLatch实例只能实现一次等待/通知。对于同一个CountDownLatch实例，latch。latch.countDown()的执行线程在执行该方法之前所执行的任何内存操作，对等待线程在latch.await()调用返回之后的代码是可见的并且有序。

#### CyclicBarrier

可以用于实现多个线程之间的相互等待，CyclicBarrier.await()既是等待方法又是通知方法，CyclicBarrier实例的所有参与方除最后一个线程外都相当于是等待线程，最后一个线程为通知线程。

#### 区别

CyclicBarrier可以重复使用，CountDownLatch不能重复使用。

CountDownLatch可以看做是一个计数器，并且操作是原子操作，同时只能有一个线程操作。给CountDownLatch对象设置一个初始值数字计数值，调用await()方法都会阻塞，直到这个值减少为0，才释放所有线程，后序的await()都将立刻返回。减小至的方法为countDown()方法。

CyclicBarrier为一个同步辅助类，允许一组线程相互等待，直到到达某个公共的屏障点。当到达指定的值的时候，释放所有线程，并且计数器置为0重新开始。await()方法计数+1，加1后的值不等于构造方法初始化的值，则阻塞。

| CountDownLatch                                               | CyclicBarrier                                                |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| 减计数方式                                                   | 加计数方式                                                   |
| 计算为0释放所有等待线程                                      | 计数达到指定值释放所有等待线程                               |
| 计数为0后无法重置                                            | 计数达到指定值，计数重置为0重新开始                          |
| 调用countDown()方法减1，调用await()方法只能阻塞，对计数没有任何影响。 | 调用await()方法计数加1，加1后的值不等于构造方法的值，则线程阻塞。 |
| 不可重复利用                                                 | 可以重复利用                                                 |

