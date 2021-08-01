# Spring 中用到的设计模式
- https://zhuanlan.zhihu.com/p/114244039
- https://juejin.cn/post/6844903849849962509
- https://cloud.tencent.com/developer/article/1593982

## 工厂模式
- BeanFactory 延迟注入
- ApplicationContext，扩展了 Bean Factory

## 工厂方法
- FactoryBean，返回的不是工厂，而是对象
- TODO: 工厂方法 vs 抽象工厂

## 单例模式
- Spring Bean 默认作用域都是单例模式
- 使用 ConcurrentHashMap 实现单例注册表

## 适配器模式
- HandlerAdapter
- 返回ModelAndView

## 装饰器模式
- Wrapper 或者 Decorator

## 代理模式
- Spring AOP
- 为目标对象创建动态的代理对象
- 日志功能 和 事务管理

## 观察者模式
- ApplicationEvent
- ApplicationListener

## 策略模式
- Spring提供不同的Resource实现类
- 不同的实现类负责不同的资源访问逻辑

## 模板方法
- Spring Template Method
