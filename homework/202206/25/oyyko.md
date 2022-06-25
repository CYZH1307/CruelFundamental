相同点

- 他们都可以用来申请动态内存和释放内存



### 不同：

#### 概念的差别

- malloc/free是C++/C语言的标准**库函数**，而new/delete是C++的**运算符**，所以对于非内部数据类型的对象而言，光用malloc/free是无法满足动态对象的要求的。对象在创建的同时需要自动执行构造函数，对象在消亡之前必须执行析构函数。由于malloc/free只是库函数，不是运算符，所以不再编译器控制范围之内，不能够把执行构造函数和析构函数的任务强加给malloc/free。因此C++语言需要一个在完成内存分配的同时也能完成初始化的运算符new，以及一个完成清理和释放内存的运算符delete。
- 在C++语言中可以随时调用C语言库函数（函数）管理内存，但是在C语言中只能使用malloc/free来管理动态内存。
- new 建立的对象，你可以把它当作是一个普通的对象，用成员函数进行访问，不要直接访问它的地址空间；malloc分配的是一片内存区域，可以直接用指针访问，而且还可以在里面移动指针。
- 从上一点可以知道，new建立的是一个对象，而malloc分配的是一块内存。
- new可以认为是malloc加上构造函数组成，delete可以认为是free加上析构函数组成。new构建的指针是带类型信息的，而malloc返回的都是void* 指针。





- new自动计算需要分配的空间，而malloc需要手动计算字节数。
- malloc函数的原型：`void *malloc(size_t size);`，当使用malloc申请一块长度为length的数据类型的内存时`int *p=(int*)malloc(sizeof(int)* length);` 我们主要看两部分，一个是类型转换，一个是分配的空间sizeof，malloc在被使用时需要指明开辟的空间的指针类型，然后malloc函数本身不识别申请的内存是什么类型，它只关心内存的总字节数。

- 函数free非常简单，它只需知道malloc函数开辟的空间的指针，通过指针的类型即可知道内存的容量，所以直接使用`free(*p)` 即可正确地释放内存。而且如果 p 是NULL指针，那么free对p操作无论多少遍也不会有问题，但是如果 p 不是NULL指针，那么free对 p 执行超过一次就会出错。



- new的用法比malloc要简单得多， `int *p1 = (int *)malloc(sizeof(int)* length);` `int *p2 = new int[length];` 因为在new内置了sizeof，类型转换和类型安全检查功能。对于非内部数据类型的对象而言，new在创建动态对象的同时完成了初始化工作。如果对象有多个构造函数，那么new的语句也可以有多种形式。但是用new创建对象数组，那么只能使用对象的无参数构造函数。 `Obj *objects = new Obj[100];//创建100个动态对象。` 不能写成 `Obj *objects = new Obj[100](1);//创建100个动态对象的同时赋值1`

在使用delete释放对象数组的时候，注意不要丢了符号[]， `delete []objects;//正确` 不要写成 `delete objects;//错误` 这样的话，只释放了第一个元素，漏掉了99个元素。



- 如前面所说，new内置了安全检查功能，而malloc没有，例如 `int *p=new float[2];//编译报错` 但是 `int *p=malloc(2*sizeof(float));//编译器无法指出错误`

- new operator由两部构成，分别是operator new和construct。其中operator new 对应malloc，但operator new 可以重载，可以自定义分配内存策略，甚至不做内存分配，甚至分配到非内存设备上，但是malloc不可以。
- 最后一点，在使用malloc/free函数时需要库文件支持，而new/delete不需要。
- 当申请的空间是内置类型时，delete和free能够混用

*                    free不能自动调用析构函数 因此对于特定的类型不能用free替代delete

