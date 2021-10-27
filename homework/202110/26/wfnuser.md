# 异常的实现机制
参考： [C++异常机制的实现方式和开销分析](http://baiy.cn/doc/cpp/inside_exception.htm)

为了实现异常，我们必须在栈框架之上加一些额外的区域。让我们可以正确捕获exception，并完成 stack unwind。
需要增加三个元素：
1. EXP* piPrev
2. EHDL* piHandler
3. int nStep

在含有 `try` block 的函数里，编译器会加上 tblTryBlocks 。
Stack Unwind 
异常捕获： 1. 比较异常发生位置是否属于某个try 通过nStep实现 2. 在 tblTryBlocks 中匹配 tlbCatchBlocks 如果没有匹配返回上一级 piPrev； 否则执行 catch 块代码。
异常抛出： call __CxxRTThrowExp

时间复杂度： 异常捕获 O(m) 和调用栈深度有关