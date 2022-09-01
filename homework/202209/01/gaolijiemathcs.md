### JAVA: volatile关键字作用

JMM，Java内存模型，规定了Java程序各种变量（线程共享变量）的访问规则。

所有的共享变量存储在主存当中，变量包括实例变量和类变量，不包括局部变量（线程私有）。

每个线程自己的工作内存，保留了被线程使用的工作副本。

线程对变量的操作（读取）只能在工作内存中，不能直接读写主存的变量。



1、volatile保证了不同线程对共享变量操作的可见性，一个线程修改了volatile修饰的变量，修改写回主存的时候，另外一个线程会立刻看到最新的值。

当有并发的时候，多个CPU访存，需要遵守缓存一致协议。

原理：写数据的时候发现是共享变量也存在副本，因此会发信号给其他CPU，通知变量缓存行无效状态，从内存读取。发现数据失效的方式，通过嗅探机制。【存在总线风暴，MESI会从主存中cas不断嗅探，无效交互会占用带宽】因此不要大量用volatile，可以结合锁一起用。



2、禁止指令重排序。无论怎么重排序，都要保证单线程执行结果不会改变。volatile会加内存屏障进行指令重排序。在volatile写在前面和后面分别插入内存屏障（StoreStore禁止上面重排序  StoreStore禁止下面重排序），volatile读在后面插入两个内存屏障（LoadLoad禁止读重排序 LoadStore禁止下面写重排序）



满足happens-before：volatile域的写操作，happens-before任意线程对这个volatile域的读。



3、无法保证原子性

i++是两条指令。要解决原子性问题，用AtomicInteger原子类。





适用场景：

- 某个属性被多个线程共享，有线程修改，其他线程可以立即得到修改后的值。作为触发器。

- volatile不是锁操作，没有提供原子性和互斥性，无锁不用花时间获取锁，成本较低。

- volatile只能用在属性上，编译器不会对这个变量做指令重排序。

- volatile提供可见性，任何线程对其的修改立刻被其他线程捡到，volatile属性不会被线程缓存，而是从主存中读取。

- volatile保证happens-before保证，对变量的写入happens-before其他线程后序对v的读取。

- 可以用在双检锁：

  ```java
  class Singleton {
      private volatile static Singleton instance = null;
      private Singleton() {}
      public static Singleton getInstance() {
          if(instance == null) {
              synchronized(Singleton.class) {
          		if(instance == null) {
                      instance = new Singleton();
                  }        
              }
          }
          return instance;
      }
  }
  ```

  

  