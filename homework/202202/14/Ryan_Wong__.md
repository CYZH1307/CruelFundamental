# 如何保证单例模式只有唯一实例？你知道的都有哪些方法？

### 1.饿汉方式：

将类的构造函数设为私有，每次构造时对类的实例判空，若为空则构建并返回一个静态实例，若不为空则返回引用。

### 2.DCL方式：

是懒汉方式的进阶版。将类的构造函数设为私有，通过静态方法返回一个静态实例，创建时加入双重判空和synchronize代码块。第一次判空是为了避免懒汉方式的每次构建都需要同步的缺点；第二次判空是为了避免重复创建实例，保证同时最多只有一个线程进入synchronize块。
