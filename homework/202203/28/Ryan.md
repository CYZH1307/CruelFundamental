# Docker: container 和 image 的区别

image是虚拟机镜像，是一个只读的模板，一个独立的文件系统，包括运行容器所需的数据，可以用来创建新的containers。

container是创建出来的实例，每个container间是相互隔离的。container中会运行特定的运用，包含特定应用的代码及所需的依赖文件。
可以把container看作一个简易版的linux环境（包含root用户权限，进程空间，用户空间和网络空间等）和运行在其中的应用程序。
