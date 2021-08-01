## 0728 - Spring中用到的设计模式

有很多，今天只学习两个。

### 工厂设计模式
（1）主要体现在 BeanFactory 或者 ApplicationContext 创建对象的时候

（2）Sample code for BeanFactory

```xml 
<beans>
  <bean id="id1" class ="...">
  </bean>
  <bean id="id2" class ="...">
  </bean>
  ......
</beans>
```

（2）Sample code for ApplicationContext

ApplicationContext 接口的实现类

```
ClassPathXmlApplication implements ApplicationContext
FileSystemXmlApplication implements ApplicationContext
XmlWebApplicationContext implements ApplicationContext
```

###  单例模式

目前，我看到比较多的就是 prototype 和 singleton 作用域。

prototype： 每次都会创建一个新的 bean， `@Scope(value = "prototype")`

singleton：默认的作用域就是单例，如果需要声明的话：  `@Scope(value = "singleton")`
