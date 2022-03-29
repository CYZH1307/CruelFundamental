#### Docker: container 和 image 的区别,如何创建一个image

* image是文件和meta data的集合，只读,负责存储，不同的image可以共享相同的layer，结构上分层
* container在images上加了container layer，可读可写，可以看做image的实例化，负责运行
