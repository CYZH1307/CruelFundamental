# 监听者模式
- https://blog.csdn.net/octobershiner/article/details/6654920

## 简述
- 用于时间监听
- 可以多个观察者监听一个主题
- 有三个接口，添加观察者，删除，和通知
- 用List保存观察者集合
- Subject::notifyListener中调用Listener::updateSignal

## 应用
- java.util.Observable类和Observer接口，支持观察者模式，但在JDK 9后被废弃
- Sping中的相关类，ApplicationEvent，ApplicationListener，ApplicationContext，ApplicationEventMulticaster
