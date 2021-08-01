# 针对你熟悉的编程语言和版本，描述锁的实现

## 

Java  jdk1.8

 Java 很多的锁时使用AQS实现的，比如CountDownLatch、Semaphore、ReentrantLock、ReentrantReadWriteLock、ThreadPoolExecutor中的Worker。

## AQS的实现原理：

AQS提供了一个阻塞的同步器的实现框架，使用volatile int state来表示当前同步器的状态，要使用AQS实现锁的类需要继承AQS并提供维护state状态的的方法，state的数值意味着当前的锁是被持有状态还是被释放状态。

AQS提供操作state的方法getState、setState、compareAndSetState是原子操作的。

AQS有两种模式，一种是独占模式，一种是共享模式，默认是独占模式。

当在独占模式的时候，一次只有一个线程可以获取锁，当在共享模式的时候，一次可以有多个线程获取锁。  

一般一个锁只实现一种模式，但是ReadWriteLock会同时实现两种模式，即读锁和写锁。

使用isHeldExclusively方法可以查看当前线程获取到的锁是独占的还是共享的。

AQS的序列化只会将state进行序列化，所以当反序列化的时候，保存线程的queue将会是空的，如果想要将锁序列化，典型的方式是定义一个readObject方法存储这些值。

如果想要继承AQS并实现锁的话，需要实现tryAcquire、tryRelease、tryAcquireShared、tryReleaseShared、isHeldExclusively方法，并且这些方法必须被用线程安全的方式实现。除了这些方法的其他方法都是final的不能被修改。

AQS维护了一个先进先出的队列，这个队列存放Node，每个Node里面包含着做一个线程，每次一个线程没有获取到锁的时候，如果不在这个队列中，就会被加入到这个队列中然后阻塞这个线程。

互斥锁：

```java
Acquire:
 *     while (!tryAcquire(arg)) {
 *        <em>enqueue thread if it is not already queued</em>;
 *        <em>possibly block current thread</em>;
 *     }
```

当有一个线程释放了锁之后，会尝试释放锁，并唤醒对列里排在最前的线程。

```
Release:
 *     if (tryRelease(arg))
 *        <em>unblock the first queued thread</em>;
 * </pre>
```

公平锁：

公平锁可以使用hasQueuedPredecessors查看等待对列里是否已经有线程在排队，如果有线程在排队则返回true，然后tryAcquire返回false；因为公平锁是要让等待时间最长的线程先获取到锁，新来的线程先排队。

AQS实现的锁默认情况下不是公平和无饥饿的，此时锁的吞吐量和可扩展性是最高的。

获取锁的过程不是通过自旋来完成的。

当AQS不能满足你要求的时候，你可以使用更底层的类atomic、queue、lockSupport来自己实现锁。

像CountDownLatch是共享锁，它也是通过继承AQS实现一个同步器并实现上面四个方法维护同步状态state。

AQS还支持在阻塞队列里等待的时候被打断。

## 描述一下ReentrantReadWriteLock的实现原理：

ReentrantReadWriteLock分成两种模式，分为两个锁，读锁和写锁，写锁是互斥的，读锁是共享的；ReentrantReadWriteLock分为两种模式公平模式和非公平模式，公平模式就是等待最久的先获得锁，非公平模式就是谁先抢到谁先获得锁。

读锁和写锁可以分别多次重入，即同一个线程多次获取一个读锁或多次获取一个写锁，当一个线程获取了写锁之后它还可以获取读锁，但是当一个线程获取读锁之后，它就不能获取写锁了。这种操作非常实用当一个线程拿到了写锁之后，调用有读操作的方法。

### 锁降级

同一个线程可以先获取到写锁在获取读锁，然后释放读锁，完成从写锁到读锁的降级，但是如果想从读锁升级到写锁是不可以的（当一个线程获取到了读锁，之后想获取写锁，必须先释放读锁，在尝试获取写锁）

获取锁的过程中被打断：

在获取锁的过程中， 线程是可以被打断的。

写锁支持Condition，读锁不支持，Condition这个类是用来监控当前的锁是被持有还是被抢占的状态的。

ReentrantReadWriteLock适用于那些读多写少的情况，在这种情况下使用ReentrantReadWriteLock的开销低于同步的开销。



#### TODO

ReentrantReadWriteLock 的实现细节



