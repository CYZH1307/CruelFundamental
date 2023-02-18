# Docker 原理
- http://blog.allposs.com/post/kubernetes/101-windows%E7%B3%BB%E7%BB%9F%E4%B8%8B%E7%9A%84docker/

## 概念
- Docker on Windows，Windows上运行Windows
- Docker for Windows，Windows上运行Linux Docker

## 运行方式
- Windows Server COntainer，通过进程和命名空间隔离技术来提供应用程序的隔离
- Hyper-V 容器，在Windows Server容器上提供隔离上扩展

## 区别
- Linux: containerd -> runC -> libcontainerd -> DockerEngine -> REST API
- Windows： ComputeService -> ...
- ComputeService 为 containerd on Windows
