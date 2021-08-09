# Reactor 模式
- https://zhuanlan.zhihu.com/p/347779760

## 介绍
- 也叫，反应器模式，应答者模式，基于事件驱动
- 拥有一个或多个并发输入源，Client
- 一个服务处理器，ServiceHandler::dispatchEvent
- 多个请求处理器，EventHandler::handleEvent
- 服务处理器将输入请求事件，以同步的多路复用方式，分发给相应的请求处理器

## 特点
- 并发处理服务请求
- 将请求分发到，多个非阻塞的请求处理器

## 单 Reactor 单线程
- 只有一个服务处理器
- 无法发挥多核处理器性能
- 只有一个事件处理器

## 单 Reactor 多线程
- 一个服务请求器 ServiceHandler 、 Reactor
- 一个链接接收器 Acceptor
- 多个时间处理器 EventHander， Worker

## 主从 Reactor 多线程
- TODO

##特点
- 异步，响应快
- 避免多线程同步和切换
- 利用多线程，扩展性好
- 复用性高，和业务分离

