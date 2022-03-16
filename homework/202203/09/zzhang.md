### C++: 一个空的类在instantiate的时候会有执行哪些操作

TODO



### Python: 简述Python的内存管理

Ref: [cnblogs](https://www.cnblogs.com/geaozhang/p/7111961.html)

一、变量与对象

1. 通过变量指针引用对象：
   - 变量指针指向具体对象的内存空间，取对象的值
2. 对象：类型已知，每个对象都包含一个头部信息（头部信息：类型标识符和引用计数器）
   - `id()` 是Python的内置函数，用于返回对象的身份，即对象的内存地址
3. 通过`is` 进行引用所指判断，`is` 是用来判断两个引用所指的对象是否相同
   - Python缓存了整数和短字符串，因此每个对象在内存中只存有一份，所用所指对象就是相同的，即使使用赋值语句，也只是创造新的引用，而不是对象本身
   - Python没有缓存长字符串、列表及其他对象，可以由多个相同的对象，可以使用赋值语句创建出新的对象。

二、引用计数

在Python中，每个对象都有指向该对象的引用总数 -- 引用计数

- 查看对象的引用计数：`sys.getrefcount()`

1. 普通引用

   - 当使用某个引用作为参数，传递给`getrefcount()`时，参数实际上创建了一个临时的引用。因此`getrefcount()`所得的结果，会比期望的多1.

2. 容器对象

   - Python的一个容器对象(List, Dic)，可以包含多个对象。
   - 容器对象中包含的并不是元素对象本身，是指向各个元素对象的引用

3. 引用计数增加

   - 对象被创建 `n=123`
   - 被别人创建引用 `m=n`

   - 作为容器对象的一个元素 `a=[1,12,123]`
   - 被作为参数传递给函数 `foo(123)`

4. 引用计数减少

   -  对象的别名被显式的销毁 `del m`
   - 对象的一个别名被赋值给其他对象 `n=456`
   - 对象从一个容器对象中移除，或容器被销毁 `a.remove(123)`
   - 一个本地引用离开了它的作用域，比如 `foo(123)` 结束

三、垃圾回收

1. 原理（引用计数相关）

   - 当Python的某个对象的引用计数降为0时，说明没有任何引用指向该对象，该对象就成为要被回收的垃圾。比如某个新建对象，被分配给某个引用，对象的引用计数变为1。如果引用被删除，对象的引用计数为0，那么该对象就可以被垃圾回收。

2. 垃圾回收时，Python不能进行其它的任务，频繁的垃圾回收将大大降低Python的工作效率；当Python运行时，会记录其中分配对象(object allocation)和取消分配对象(object deallocation)的次数。当两者的差值高于某个阈值时，垃圾回收才会启动。

3. 在 gc模块中看阈值方法

   ```python
   import gc
   gc.get_threshold()
   # (700,10,10)
   ```

   - 700即是垃圾回收启动的阈值；
   - 每10次0代垃圾回收，会配合1次1代的垃圾回收；而每10次1代的垃圾回收，才会有1次的2代垃圾回收；

4. 分代回收

   - Python将所有的对象分为0，1，2三代
   - 所有的新建对象都是0代对象
   - 当某一代对象经历过垃圾回收，依然存活，就被归入下一代对象

5. 内存池机制

   - Python中有分为大内存和小内存：（256K为界限分大小内存）

   - 大内存使用malloc进行分配，小内存使用内存池进行分配

   - Python内存池：

     - 第三层：最上层，用户对Python对象的直接操作
     - 第一、二层：内存池，有Python的接口函数PyMem_Malloc实现-----若请求分配的内存在1~256字节之间就使用内存池管理系统进行分配，调用malloc函数分配内存，但是每次只会分配一块大小为256K的大块内存，不会调用free函数释放内存，将该内存块留在内存池中以便下次使用
     - 第零层：大内存-----若请求分配的内存大于256K，malloc函数分配内存，free函数释放内存。
     - 第-1、-2层：操作系统进行操作

     