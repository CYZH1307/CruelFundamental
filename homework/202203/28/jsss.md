# Docker: container 和 image 的区别，如何创建一个image

容器和镜像的关系类似于程序和进行的关系. 即容器是镜像的一个运行实例, 先构造`image`, 然后运行`image`即是容器.

## 创建image

1. 使用`Dockerfile`
2. 从`dockerhub`拉取别人构建好的`image`