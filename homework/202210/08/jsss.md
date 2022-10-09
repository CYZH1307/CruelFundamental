# C++：thread和pthread的区别？

`thread`是c++11标准库提供的多线程编程组件, linux平台下会使用`pthread`来实现. `pthread`是linux平台的多线程标准. 

`thread`屏蔽了底层平台的差异.
