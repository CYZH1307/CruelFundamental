# Spring 启动流程
- https://juejin.cn/post/6895341123816914958

## 启动类
- @SpringBootApplication，主程序最好在根目录下
- call SpringApplication.run()
- 有三个注解组成
- @ComponentScan，加载当前目录下所有符合要求的类，比如@Component和@Repository
- @EnableAutoConfiguration，加载AutoConfigurationImportSlector.selectImports()
- @SpringBootConfiguration，标注当前是配置类，将@Bean放入容器
- TODO: spring.factories

## SpringApplication类
- 创建一个实例，完成初始化工作，
- 包括监听器，初始化器，类加载等，然后运行



