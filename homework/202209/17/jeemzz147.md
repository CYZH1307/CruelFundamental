### 设计模式原则？

单一职责原则（Single Responsibility Principle）：应该有且仅有一个原因引起类的变更。

开闭原则（Open Closed Principle）：一个软件实体类，如类、模块和函数应该对扩展开放，对修改关闭。

里氏替换原则（Liskov Substitution Principle）：所有引用基类的地方必须能透明地使用其子类的对象。

迪米特法则（Law of Demeter）：一个对象应该对其他对象有最少的了解，即一个类应该对自己需要耦合或调用的类知道得最少，只关注自己调用的public方法，其他的一概不关心。

接口隔离原则（Interface Segregation Principle）：客户端不应该依赖它不需要的接口；一个类对另一个类的依赖应该建立在最小的接口上。

依赖倒置原则（Dependence Inversion Principle）：高层模块不应该依赖低层模块，二者都应该依赖其抽象；抽象不应该依赖细节；细节应该依赖抽象。
