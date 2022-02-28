### C++ 简述vtable

类的虚表是一个指针数组，每个元素对应一个虚函数的函数指针。

- 类的每个虚函数占据虚函数表中的一行，如果类中有N个虚函数，那么其虚函数表将有N*4字节的大小。
- 虚表内的条目，即虚函数指针的赋值发生在编译器的编译阶段，也就是说在代码的编译阶段，虚表就可以构造出来了。
- 虚表属于类的，而不是某个具体对象。编译器会为每个有虚函数的类创建一个虚函数表，该虚函数表将被该类的所有对象共享。
- 为了指定对象的虚表，对象内部包含一个虚表的指针 `*__vptr`，来指向自己所使用的虚表。
- 一个继承类的基类如果包含虚函数，那个这个继承类也有拥有自己的虚表，故这个继承类的对象也包含一个虚表指针，用来指向它的虚表
  - 类A是基类，类B继承类A，类C又继承类B
  - 由于这三个类都有虚函数，故编译器为每个类都创建了一个虚表。类A，类B，类C的对象都拥有一个虚表指针，*__vptr，用来指向自己所属类的虚表
  - 类A包括两个虚函数，故A vtbl包含两个指针，分别指向`A::vfunc1()`和`A::vfunc2()`。类B继承于类A，故类B可以调用类A的函数，但由于类B重写了`B::vfunc1()`函数，故B vtbl的两个指针分别指向`B::vfunc1()`和`A::vfunc2()`。类C继承于类B，故类C可以调用类B的函数，但由于类C重写了`C::vfunc2()`函数，故C vtbl的两个指针分别指向`B::vfunc1()`（指向继承的最近的一个类的函数）和`C::vfunc2()`
  - 总结：对象的虚表指针用来指向自己所属类的虚表，虚表中的指针会指向其继承的最近的一个类的虚函数

- 我们把**经过虚表调用虚函数**的过程称为**动态绑定**，其表现出来的现象称为**运行时多态**。动态绑定区别于传统的函数调用，传统的函数调用我们称之为静态绑定，即函数的调用在编译阶段就可以确定下来了。



### Python 2 和 Python 3 的区别

除法：

- Python 2 的结果是整型；Python 3的结果是浮点类型

- `//` floor 除法，会对除法的结果自动进行一个 floor 操作，在 Python 2.x 和 Python 3.x 中是一致的。

- 如果要截取整数部分，那么需要使用 math 模块的 trunc 函数

  ```python
  >>> import math
  >>> math.trunc(1 / 2)
  0
  >>> math.trunc(-1 / 2)
  0
  ```



导入：

- Python2中的包导入顺序：标准库—相对倒入（即当前目录）—绝对导入（sys.path）
- Python3中的包导入顺序：标准库—绝对导入（如果想要相对导入，使用from .moudel）

类：

- Python2中默认类是旧式类，需要显式继承新式类(object)来创建新式类
- Python3中完全移除旧式类，所有类都是新式类，但仍可显式继承object 类

异常：

- Python2中异常链会丢失原始异常信息，即：处理B异常时引发了A异常，B异常信息会丢失
- Python3中将原始异常信息赋值给__context__属性。并且可以显式指定一个异常作为另一个异常的子句：raise DatabaseError() from IOError()

字典：

- Python2中的 dict 类中的keys、values和items均返回list对象，iterkeys、itervalues和iteritems返回生成器对象。
- Python3中**移除了list、只返回一个生成器的对象**，只保留视图（生成器），但方法名为：keys、values和items。同时去掉的还有 dict.has_key()，可用in替代它。

模块合并：

- Python2中的 StringIO 和 cStringIO 合并为 Python3 中的`io`
- Python2中的 pickle 和 cPickle 合并为 Python3 中的`pickle`
- Python2中的 urllib、urllib2 和 urlparse 合并为 Python3 中的`urllib`

数据类型：

- Py3.X去除了long类型，现在只有一种整型——int，但它的行为就像2.X版本的long

- 新增了bytes类型，对应于2.X版本的八位串，定义一个bytes字面量的方法如下： 

  - ```python
    >>> b = b'abcde' 
    >>> type(b) 
    <type 'bytes'> 
    ```

- str 对象和 bytes 对象可以使用 .encode() (**str -> bytes**) 或 .decode() (**bytes -> str**)方法相互转化。

重命名：

- ConfigParser -> `Configparser`
- itertools.ifilter -> `filter`
- raw_input -> `input`
- itertools.imap -> `map`
- xrange -> `range`
- reduce -> `functools.reduce`
- socketServer -> `socketserver`
- itertools.izip -> `zip`
- Queue -> `queue`



