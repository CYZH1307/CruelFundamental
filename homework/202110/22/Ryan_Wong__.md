# C++内存区域 #
主要分为5块，从上到下依次为：  
    
    1.stack区 ：编译器自动分配自动释放的空间，从上到下增长，所有在函数内直接声明的变量都存在这里。
    2.库文件区：所有被动态调用的库文件都存在这里。
    3.heap区：程序员手动分配手动释放的空间，从下到上增长，所有malloc、new的变量都存在这里。
    4.全局变量区：存放global和static的变量，从上到下还能细分为两层：data区和bss（Block Started by Symbol）区，前者存放已经初始化的这些变量，后者存放未初始化的这些变量。
    5.代码区：存放编译好的二进制代码文件。
