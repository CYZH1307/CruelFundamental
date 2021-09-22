# K8S 为何舍弃 Docker

ref: 
- https://www.infoq.cn/article/vasfwochd6jol5avhfaz
- https://acloudguru.com/blog/engineering/kubernetes-is-deprecating-docker-what-you-need-to-know
- https://www.zdnet.com/article/kubernetes-dropping-docker-is-not-that-big-of-a-deal/

## bg

k8s 1.20版本后弃用docker shim到1.23之间

docker是在containerd之上的一个工具的集合

k8s的cri 容器运行时接口可以直接调用cri-containerd

k8s社区自己有crio

k8s仍然可以运行使用docker的OCI计划，可以使用dockerfile 和 其image

docker会成为ci中的一部分而不是k8s生产环境中的一部分