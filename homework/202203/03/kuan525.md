#### C++: 简述 static member variable 和 global variable 的区别

##### global variable：

​	定义在函数外部的变量，全局变量可以为本project中其他函数所共用，它的有效范围从定义变量的位置开始到本源文件结束。建立全局变量的作用是增加了函数间数据联系的渠道。

​	extern关键字可以将全局变量的作用域扩展至其他源程序。在应用之前用关键字extern对该变量作‘外部变量声明’。表示该变量是一个已经定义了的全局变量。有了此声明，就可以从声明处起，合法的调用该外部变量。

##### static member variable：

所有未加修饰的local variable都是auto的，而所有未加修饰的global variable都是static的！

static全局变量：通常static全局变量应用于程序中希望某些全局变量只限于被本文件引用，而不能被其他文件引用。即static全局变量的作用域只在本文件。

static局部变量：如果static放在function外面，那么就具有全局作用域。如果放在函数内部，就只有局部作用域。但不管是局部static还是全局static，他们的lifetime（生命周期）都是一样的，一直持续到整个源程序的结束。（作为函数返回值的时候能够体现出来）

 总结：

一、所有未加修饰的global variable都是static 。

二、static 作为全局的和局部的lifetime是一致的（作为函数返回值的时候能够体现出来）

三、static全局变量的作用域只在本文件。