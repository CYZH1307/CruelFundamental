Yarn原理
ResourceManager：资源统一调度、管理。处理客户端请求，启动ApplicationMaster，监控NodeManager健康情况。
NodeManager：负责节点资源管理使用，处理RM命令，协助AM处理任务，向RM汇报节点资源情况，管理Container。
Container：封装节点的运行资源，内存、cpu等。容器中运行Map或Reduce任务。
ApplicationMaster：给应用程序申请资源并分配任务。

应用程序提交给yarn，RM分配资源，与NM通信，建立Container运行AM实例。AM向RM注册申请资源，AM在对应的NM启动Container，开始执行任务。AM监控执行情况，并反馈给RM。当应用程序完成，Container停止，AM从RM中注销。
