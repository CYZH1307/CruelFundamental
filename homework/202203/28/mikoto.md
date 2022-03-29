### Docker: container 和 image 的区别

1. image:
- 文件和meta data的集合
- 分层的，且每一层都可以增删改文件，
- 不同image可以共享相同的layer
- read-only

2. container
- 通过image创建
- 在image layer上建立一个container layer（可读写）
- image负责app的存储和分发，container 负责运行app

总结：镜像由一层层只读层堆叠在一起，而容器为镜像只读层+读写层
