# ThreadLocal是否会造成内存泄露
- https://mp.weixin.qq.com/s/pMu7IGUDFwV0drf-Vwl4Sw

## 本地线程变量
- 解决数据访问竞争
- 用于多租户，全链路眼力测试，链路跟踪中保存线程上下文环境
- 在一个请求流转中非常方便地获取关键信息，比如当前租户，压测标记
- 每个线程会维护私有属性，ThreadLoca.ThreadLocalMap threadLocals
- Key为ThreadLocal对象

## 弱引用
- 强引用，Java默认引用类型，如Object obj = new Object()
- 只要有强引用指向一个对象，JVM就不会回收
- TODO: 软引用，年轻代不会回收，永久代才会回收
- TODO: 弱引用，会被年轻代回收
- 虚引用，垃圾回收器不能回收，在垃圾回收后通过注册PhantomeReference队列来通知应用程序回收
- Strong / Soft / Week / Phantome

## 结果
- 值不存储在ThreadLocal 对象中，而是用过对象的set方法，其实为线程
- 线程生命周期内，值对象不会回收，因为有可能内存泄漏

## 解决办法
- TODO
