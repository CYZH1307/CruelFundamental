# Yarn, 20211114
- a framework to manage resources and schedule tasks
- 包括资源管理器，节点管理器，程序主节点
- 资源管理器, monitor, allocate and manage resource
- 节点管理器, maintain each node, send heartbeat to resouce manager
- 程序主节点, schedule and coordinate an application
- Client --> 资源管理器 --+--> 节点管理器
-                        +--> 程序主节点
-                        +--> 容器


TODO:
- RM只接受NM的资源回报信息，对于具体的资源处理则交给NM自己处理。
