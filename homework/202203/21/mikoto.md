## C++中struct和class的区别， C语言struct和C++ struct的区别？

C++中struct和class的区别？
- 默认访问控制权限不同，struct默认public，class默认private
- 默认继承关系不同，struct默认public，class默认private
- class关键字可以用于定义模板参数，而struct不可以，例如：
```
template<typename T> // typename 可以换成class而不能换成struct
void func(const T& t) {}
```

C语言struct和C++ struct的区别？

1. C++ struct相比于C struct进行了扩充，C中struct不能有成员函数、静态成员、默认访问控制权限为pubilc且不支持修改，不支持继承，不能直接初始化数据成员等，而C++ struct中可以有成员函数、静态成员，支持三种访问控制权限public/private/protected等，支持从类或其他结构体继承，支持直接初始化数据成员等。

2. 使用时区别，C中结构体使用需要加struct关键字，或者对结构体使用typedef取别名，而C++ 中可以直接使用。例如：
```
struct Student{
    int age;
    string name;
}

typedef struct Student Student2; //C中取别名

struct Student stu1 // C中正常使用
Student2 stu2 // C中通过别名使用
Student stu2 // C++中使用

```
