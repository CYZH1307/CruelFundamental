# 观察者模式

这是一种对象间一对多的依赖关系，当这个“一”的状态被改变时，所有“多”的依赖它的对象都会得到更新通知。前者被称为目标对象，后者被称为观察者对象。一个模型例子就是“发布-订阅”模型。

### 使用场景：

1. 抽象模型中有两个方面，其中方面a的行为依赖方面b的动作时，可将a设为观察者对象、b设为目标对象，采用该模式。
2. 一个对象的变化会引发其他对象的变化，但并不知道其他对象的个数时，可采用该模式。
3. 一个对象的变化必须通知其他对象，但不知这些对象的个数和实现时，也可采用该模式。
4. 存在事件触发链时，比如对象a的行为影响b对象，b对象行为又影响c对象......，可用该模式创建一种链式触发机制。

### 优点：

1. 实现表示层和数据逻辑的解耦，目标对象和观察者对象只需定义好交互接口，他们各自内部数据逻辑互不干预。
2. 支持广播



### 缺点：

1. 目标对象通常不知道观察者对象集合的规模，遍历通知它们可能时间效率很低。
2. 事件触发链有可能变成循环依赖，从而导致系统崩溃。
3. 观察者并不能知道目标内部的运行逻辑，能做出的动作也相应地比较单调。
