### Docker: container 和 image 的区别,如何创建一个image

#### 区别

Image:

image本身read only；负责存储，是文件和meta data的集合；支持分层且每一层可以添加删除文件成为新的image；不同image可以共享相同layer。

container:

通过image复制创建；容器=image层+读写层，container可以看做是images的一种具体表现。image负责存储和分发，container负责运行。

#### 创建image

1.Build from Dockerfiler

2.Pull from Registery



https://www.cnblogs.com/xybc/p/13527965.html