# C++: 描述内存分配方式以及它们的区别

## 静态存储

定义在所有函数外部，在代码编译时建立，从静态存储区域分配，并会在程序运行时一直存在，如static变量、全局变量。

## 栈上

定义在作用域里，栈内存分配位于CPU指令集，作用域完结时自动释放。

## 堆上

由程序员使用malloc/new分配，free/delete释放。生存期由程序员决定，最为灵活。
