Q：nacos 原理

A：
nacos是针对微服务架构中服务管理的解决方案。其功能有：服务发现。配置信息集中管理，动态更改。基于DNS协议服务发现。服务和元数据管理。

1 配制管理功能
（1）nacos的配置管理功能：在微服务架构中构建配制中心，针对应用程序的配置信息进行统一管理。用户把配置发布到配置中心中。各个服务通过远程协议从配置中心获取配置信息。
（2）nacos配制管理模型：通过namespace、group、data id 获取配置信息。namespace用于隔离配制文件，应用不同的开发环境；group对应具体项目；data id对应项目的配制文件。

2 服务发现功能
微服务间的协作通过远程调用实现，nacos使服务能够通过DNS或HTTP发现其他服务。微服务将地址注册到服务发现中心，当服务消费方调用服务生产方时，从服务发现中心获取生产方的地址，进行远程调用。
