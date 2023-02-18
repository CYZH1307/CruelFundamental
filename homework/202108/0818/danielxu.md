# docker

## components
* Client
    * 用于和 daemon 交互, 创建/删除/ container 或者 image 等
* Image
    * 由不同的文件组成, 包括并不限于 应用程序, 依赖, 安装程序等
    * 只读的多层 layer 构成
    * 用于生成 container
* Registry
    * 存放docker image 的地方
* Container
    * 就是隔离起来的 process, 如果process 运行结束, container 就会退出. 这也是为什么 docker container 比 vm 更轻量的原因. 
    * namespace 用于隔离
    * cgroup 用于资源限制