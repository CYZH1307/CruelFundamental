# Kubernetes为什么要弃用Docker？
- https://cloud.51cto.com/art/202106/665227.htm
- https://bbs.huaweicloud.com/blogs/detail/223165
- https://www.infoq.cn/article/vasfwochd6jol5avhfaz

## 经过
- K8S，Kubernetes 容器编排领域的标准，Docker是K8S的默认容器引擎
- 2020/12 K8S 决定移除 Dockershim 相关代码
- Kubelet --------- CRI ----+---- Containerd
-                    |      +---- CRI-O
-                    +-- Dockershim -- Docker
- Docker 垫片有社区维护，Dockershim 将请求转发给 Docker 服务
- K8S 引入容器运行时接口 CRI，隔离不同容器运行时的实现机制，不依赖某个具体的运行时实现
- Docker 没打算支持 K8S 的 CRI 接口，需要 K8S 社区维护 Dockershim
- CRD -> API
- CNI -> Network
- CRI -> Containerd
- CSI -> Storage
- Scheduling Framework -> Scheduler
- K8S 在 1.3 支持 rkt 和 Docker 两套运行时，维护困难
