## 针对你熟悉的编程语言和版本，描述锁的实现

### Java中的锁有两种实现方式：JVM提供的synchronized关键字、JDK中的Lock接口；

synchronized修饰普通同步方法 ：锁的是对象
synchronized void lock(){ }
修饰静态同步方法：修饰静态方法锁定的是这个类的所有对象
synchronized static void lock(){}
修饰一个对象（也叫修饰一个代码块）：跟修饰普通同步方法一样。锁的是对象
void lock(){ synchronized (this){ } }
修饰一个类：跟修饰静态方法是一样的，所有对象共用一把锁
void lock(){ synchronized (obj.class){ } }


任意对象都可以作为同步锁。所有对象都自动含有单一的锁（监视器）。
同步方法的锁：静态方法（类名.class）、非静态方法（this）
同步代码块：自己指定，很多时候也是指定为this或类名.class


### synchronized底层原理

synchronized是通过持有和释放monitor对象实现的，每一个对象都有对应的monitor对象，监视器即synchronized(obj)中的obj

但是对于同步代码块和对象，实现方式如下：

代码块：通过 monitorenter 和 monitorexit 指令实现；当我们用synchronized去修饰代码块，编译的时候就会在代码块开头和结尾插入monitorenter和monitorexit指令（编译后的指令码我们可以通过javap -c xx.class查看）；执行monitorenter的时候，尝试获取对象的锁，如果锁的计数器为0，获取monitor成功，计数器+1，执行monitorexit，则-1释放

方法：通过ACC_SYNCHRONIZED区分是否是同步方法，如果是，则执行前，先持有monitor对象

### lock与synchronized区别
Lock是显式锁（手动开启和关闭锁，别忘记关闭锁），synchronized是隐式锁，出了作用域自动释放
Lock只有代码块锁，synchronized有代码块锁和方法锁
使用Lock锁，JVM将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性（提供更多的子类）
