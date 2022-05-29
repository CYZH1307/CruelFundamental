### C++: 讲讲C++11的新特性

#### 模板相关

##### decltype关键字

可以编译器推导表达式类型

##### 可变参数模板

```c++
template<typename T,typename... Args>
void foo(const T &t, const Args& ... reset)
{
    //...
}
```

##### using

可以用using替代typeof，能方便很多。

#### 比较眼熟的概念：

##### 右值

移动语义，完美转发

##### 智能指针

(感觉就是记了个cnt)

