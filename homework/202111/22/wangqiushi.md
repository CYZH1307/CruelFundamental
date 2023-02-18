![image](https://user-images.githubusercontent.com/80054116/142963619-a13df5a3-3f29-4794-9723-13f2a26b15c2.png)
-最大区别在服务调用方式，Dubbo是RPC，而Spring Cloud 是基于HTTP的REST方式，牺牲了服务调用的性能，但也规避了RPC带来的问题。REST相比RPC更灵活，不存在代码级别的强依赖，在强调快速演化的微服务环境下，显得更加合适。

-Spring Cloud功能更全面、强大，与其他Spring项目很好的融合，在Spring Source整合下，做了很多兼容性测试，保证高可用。Dubbo各个环节功能实现是自由选择的，出问题的可能性也大。

-社区活跃度与更新力度：Spring Cloud活跃度要高于Dubbo 活跃度。Dubbo：由于新的需求，需要开发者自行升级，中小公司没有能力修改Dubbo 源码+周边的一整套解决方案，也没有在真实线上生产环境测试过，对于中小公司不太适用。Spring Cloud 更新非常快。

-Dubbo的定位是一款RPC框架，Spring Cloud是微服务架构下一站式解决方案。面临微服务基础框架选型Dubbo与SpringCloud只能二选一。Dubbo之后会积极寻求适配到Spring Cloud 生态。
