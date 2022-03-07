### C++: 简述 static member variable 和 global variable 的区别

------

##### global variable

就如字面意思，所定义的变量对本文件以及其他会访问本文件的代码均可见。

##### static member variable

static最重要的作用是改变变量的最上层作用域为当前文件，也就是只能在本文件中访问，对于其他文件来说均是隐藏的，这个作用主要还是针对链接的时候。

同时，还能可持久化变量，让变量的生存周期和全局变量一致。