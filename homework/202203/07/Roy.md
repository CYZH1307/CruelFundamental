### C++ class initialization 的顺序



存在derived class时的初始化顺序：

1. Base类的static variable, global variable
2. Derived 类的static variable, global variable
3. Base类的成员变量
4. Derived类的成员变量



如果A class里面有一个member是B class的instance，A class 从class D derived得到，在创建A的instance的时候，initialization的顺序是怎样的。

- class D
- class B
- class A





### Python: 简述Python的异常机制

##### try/except 语句

```python
try:
	<语句>        # 执行代码
except <名字>：
	<语句>        # 发生异常时执行的代码
else:
	<语句>        # 没有异常时执行的代码
finally:
  <语句>				# 不管有没有异常都会执行的代码
```

try 语句按照如下方式工作；

- 首先，执行 try 子句（在关键字 try 和关键字 except 之间的语句）。
- 如果没有异常发生，忽略 except 子句，try 子句执行后结束。
- 如果在执行 try 子句的过程中发生了异常，那么 try 子句余下的部分将被忽略。如果异常的类型和 except 之后的名称相符，那么对应的 except 子句将被执行。
- 如果一个异常没有与任何的 except 匹配，那么这个异常将会传递给上层的 try 中。



##### 抛出异常

```python
raise [Exception [,args [, traceback]]]
# Example
x = 10
if x > 5:
    raise Exception('x 不能大于 5。x 的值为: {}'.format(x))
```





##### 一些建议

- 异常只应该用于处理非正常的情况，不要使用异常处理来代替正常的流程控制。对于一些完全可预知，而且处理方式清楚的错误，程序应该提供相应的错误处理代码，而不是将其笼统地称为异常。
- 不要在try里放大块代码，而应该把大块的 try 块分割成多个可能出现异常的程序段落，并把它们放在单独的 try 块中，从而分别捕获并处理异常。
- 不要忽略异常：既然己捕获到异常，那么 except 块理应做些有用的事情，及处理并修复异常。except 块整个为空，或者仅仅打印简单的异常信息都是不妥。对异常进行合适的修复，然后绕过异常发生的地方继续运行；或者用别的数据进行计算，以代替期望的方法返回值；或者提示用户重新操作。
- 重新引发新异常：把在当前运行环境下能做的事情尽量做完，然后进行异常转译，把异常包装成当前层的异常，重新传给上层调用者。在合适的层处理异常。如果当前层不清楚如何处理异常，就不要在当前层使用 except 语句来捕获该异常，让上层调用者来负责处理该异常。
