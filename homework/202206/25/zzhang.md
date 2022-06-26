new delete是C++中的操作符，大多数实现中new底层调用的都是malloc

new的好处在于不用特别计算空间大小，返回的指针不用强制转换

malloc需要们根据循要申请的类型计算实际占用的空间大小，而且返回值是一个void * 需要自己转换。

new delete运算符 成对出现，malloc/free同样是成对出现成对使用，不能混用，即new申请的内存不能由free释放，malloc申请的内存不能由delete释放。

delete 和 delete[] 的区别，delete用来释放ptr指定的内存，而delete []适用于申请对象数组后，不仅用来释放ptr指向的内存，还逐一调用数组每一个对象的destructor
