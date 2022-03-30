### Java: 说说 sleep() 方法和 wait() 方法区别和共同点?

相同点：

- 两者都会暂停线程

不同点：

- wait()释放其获得的monitor或锁，释放锁以后会进入等待锁定池，只有notify()以后才会处于就绪状态；sleep()仍然会继续持有monitor或者锁。
- wait()用于线程间通信进行线程同步(synchornized环境调用)，sleep()用于短时间暂停当前线程，可以在任何地方使用。。
- sleep()属于Thread类的静态方法，作用于当前线程，wait()方法是Object的实例方法，作用于对象本身，只能等待其他线程调用这个实例的notify/notifyAll()才会被唤醒。
- wait()通常有条件执行线程一直处于wait状态等待某个条件成立，但是sleep()仅仅是睡眠状态。
- 执行完sleep()可以通过超时或调用interrupt()唤醒休眠线程；执行wait()通过notify()或者notifyAll()方法唤醒等待线程。
- sleep()可能会抛出异常，需要异常处理，wait()不需要。



|            | wait                                                         | sleep                                   |
| ---------- | ------------------------------------------------------------ | --------------------------------------- |
| 同步       | 只能在同步上下文中调用wait方法，否则或抛出IllegalMonitorStateException异常 | 不需要再同步方法或同步块中调用          |
| 作用对象   | wait方法定义在Object类中                                     | sleep方法定义在Thread中，作用于当前线程 |
| 释放锁资源 | 是                                                           | 否                                      |
| 唤醒条件   | 其他线程调用对象的notify()或者notifyAll()方法                | 超时或者调用interrupt()方法             |
| 方法属性   | wait是实例方法                                               | sleep是静态方法                         |



补充：

sleep()和yeild()方法的区别

- sleep()方法给其他线程运行机会的时候不考虑线程优先级，低优先级线程有机会运行。yeild()会考虑线程优先级，高优先级优先。
- sleep()执行后阻塞，yield() 转入就绪。
- sleep()有可能抛出异常，yeild()不会抛出异常。
- sleep()可移植性更好。