## 2022/03/28

### Docker: container 和 image 的区别

#### image：

文件和meta data的集合，不同的image可以共享相同的layer，image本身是read-only的

#### Container：

通过Image创建，在Image layer之上建立container layer，类比面向对象：类和实例，Image负责app的储存和分发，Container负责运行app

 镜像由一层层只读层堆在一起，容器为镜像只读层+读写层，运行态容器为由一个可读写的文件系统，静态容器+ 隔离的进程空间和其中的进程构成。

### 如何创建一个image



#### ref：

[Docker中容器（Container）和镜像（Image）的区别-云社区-华为云 (huaweicloud.com)](https://bbs.huaweicloud.com/blogs/281603)
