### Java: 引用类型有哪些?有什么区别?

强引用、软引用、弱引用、虚引用

- 强引用：传统引用，Object obj = new Object()； 无论什么情况下，只要强引用关系还在，垃圾回收器就不会回收掉被引用的对象。
- 软引用：描述一些还有用，但是非必须的对象，被软引用关联的对象，系统要内存溢出异常之前，会对这些回收，如果这次回收还没有足够内存，则抛出内存异常。SoftReference
- 弱引用：非必须对象，弱引用对象关联的，只能生存到下一次垃圾回收。WeakReference。
- 虚引用：幽灵引用，最弱的关系，一个对象如果被虚引用，不会对生存时间影响，无法通过虚引用获得对象，这个引用唯一用处，用于当这个对象被垃圾收集器回收的时候能有一个系统通知。PhantomReference。

