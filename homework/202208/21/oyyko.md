右值和左值是C++的两种值类型。

左值具有地址，可以被取出地址，可以放在等式左边，右值没有。

右值引用即对右值的引用。可以用来实现移动语义等。

万能引用指的是在模版推导中`T&&`既能正确推导出左值引用也能推导出右值引用的现象。