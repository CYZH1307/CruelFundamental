### C++: class initialization 的顺序。

### 如果A class里面有一个member是B class的instance，A class 从class D derived得到，在创建A的instance的时候，initialization的顺序是怎样的。

------

C++初始化实例得时候遵循从左往右，自底向上得原则。因此，会先初始化继承的基类，再初始化自身类中的变量。

在初始化基类的时候，会**按照继承的顺序去初始化**，而不是按照初始化列表中的顺序。

在初始化自身变量的时候，会**按照变量定义的顺序去初始化**，而不是按照初始化列表中的顺序。

初始化列表感觉就是用来放一些初始化用的参数，而不是做调用。

TODO：：虚继承的初始化