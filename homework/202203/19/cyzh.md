## 2022/03/19

### C++: 指针和引用的差别

```c++
int a = 666;
int *p = a;//指针
int &b = a;//引用
```

### 区别：

- 指针是一个变量，而引用只是一个别名
- 指针可以指向NULL，引用不可以
- 引用必须初始化，而且初始化后不能更改，指针随意一点
- 指针可以有多级，引用只能是一级

