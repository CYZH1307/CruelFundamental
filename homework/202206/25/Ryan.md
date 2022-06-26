# C++: new/delete和malloc/free之间有什么关系？delete和delete[]有什么区别？使用malloc申请的内存能否通过delete释放？使用new申请的内存能否使用free释放?

## 关系

new/delete和malloc/free的上层封装，方便直接对类使用。

## delete和delete[]有什么区别？

后者可以释放掉一个类的实例数组。

## 使用malloc申请的内存能否通过delete释放？使用new申请的内存能否使用free释放?

在基本类型里面运行不会失败，但一旦涉及类的构造/析构函数，就不能这样张冠李戴，尤其是new申请的类实例内存不能用free释放，这样会造成析构函数未调用，引起潜在的内存泄露，而且很难检查出来。
因此在C++里无论任何类型，都不应该交叉混搭使用new/delete和malloc/free。
