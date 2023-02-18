# K8S

## 抽象

K8S 将硬件基础设施抽象为一个大的资源池, 隐藏了底层的细节

## component

当应用通过 应用描述文件提交到 master 节点时, K8S 会将应用部署到集群里的 worker 节点上

* 主节点
    * etcd
        * 存储配置信息 source of truth
    * scheduler
        * 资源调度
        * 是否需要新的 pod
        * 应用需要部署到具体哪个 worker 节点
    * API server
    * controller manager
        * 故障检测
        * 自动扩展
        * 滚动更新
* worker 节点
    * kube proxy
        * 服务发现
        * 负载均衡
    * container runtime
        * 镜像管理
        *  Pod 和容器的运行
    * kubelet
        * 管理pods
        * pod: 原子调度单元

