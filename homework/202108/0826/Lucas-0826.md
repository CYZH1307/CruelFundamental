# Linux 的动态链接库和静态连接块
- https://www.zhihu.com/question/20484931

## 静态链接
- 源代码编译成目标文件
- 引用库一起链接到可执行文件中
- 优点：移植安装方便
- 缺点：浪费空间和资源
- gcc -c util.cpp -> util.o
- ar -crv libutil.a util.o -> libutil.a

## 动态链接
- 将库函数的链接载入推迟到运行时
- 实现进程之间的资源空想，即共享库
- gcc -c -fPIC util.cpp -> util.so
- 产生于位置无关的代码，全部使用相对地址
- gcc -shared util.o -o libutil.so
