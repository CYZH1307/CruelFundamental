## C++: class initialization 的顺序。如果A class里面有一个member是B class的instance，A class 从class D derived得到，在创建A的instance的时候，initialization的顺序是怎样的。

C++构造的时候的顺序：
1. 构造基类
2. 构造成员
3. 执行自己的构造函数

所以上面的构造顺序是：
1. 构造D
2. 构造B
3. 执行A的构造函数