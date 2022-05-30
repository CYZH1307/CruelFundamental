### Java: 讲一讲DDD（阿里），Reactor模型（美团、饿了么、阿里etc）

#### DDD模型

DDD的核心思想就是从业务角度出发，根据限界上下文划分业务的领域边界，定义领域模型，确认业务边界。在微服务落地时，建立业务领域模型和微服务代码模型的映射关系，从而保证业务架构和微服务系统架构的一致性。

https://www.cnblogs.com/whgk/p/15191413.html#%E4%BB%80%E4%B9%88%E6%98%AF%E9%99%90%E7%95%8C%E4%B8%8A%E4%B8%8B%E6%96%87

#### Reactor模型

特点：事件驱动（event handling）、可以处理一个或多个输入源（one or more inputs）、通过Service Handler同步的将输入事件（Event）采用多路复用分发给相应的Request Handler（多个）处理

处理方式：同步的等待多个事件源到达（采用select()实现）、将事件多路分解以及分配相应的事件服务进行处理，这个分派采用server集中处理（dispatch）、分解的事件以及对应的事件服务应用从分派服务中分离出去（handler）

https://juejin.cn/post/6844903636422623240