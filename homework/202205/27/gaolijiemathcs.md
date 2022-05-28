### Spring Bean的生命周期

- Bean 容器找到配置文件中的Spring Bean的定义
- Bean容器利用Java Reflection API创建一个Bean的实例
- 如果涉及到一些属性，利用set()方法设置一些属性。
- 如果Bean实现了BeanNameAware接口，调用setBeanName()的方法，传入Bean的名字。
- 如果Bean实现了BeanClassLoaderAware接口，调用setBeanClassLoader()方法，传入ClassLoader对象的实例。
- 如果Bean实现了BeanFactoryAware接口，调用setBeanClassLoader()方法，传入ClassLoader对象的实例。
- 与上面类似，如果实现了其他*.Aware接口 就调用其他方法。
- 如果有和加载这个Bean的Spring容器相关的BeanPostProcessor对象，执行postProcessBeforeInitialization()方法。
- 如果Bean实现了InitializingBean接口，执行afterProperitiesSet()方法。
- 如果Bean在配置文件中的定义包含init-method属性，执行指定的方法。
- 如果有和加载这个Bean的Spring容器相关的BeanPostProcessor对象，执行postProcessAfterInitialization()方法。
- 当要销毁Bean的时候，如果Bean实现了DisposableBean接口，执行destroy()方法。
- 当要销毁Bean的时候，如果Bean在配置文件中的定义包含destroy-method属性，则执行指定的方法。



小结：

```
1、实例化bean对象
2、设置对象属性
3、检查Aware相关接口，并设置相关依赖。
4、BeanPostProcessor前置处理
5、检查是否InitialzingBean以决定是否调用afterPropertiesSet方法
6、检查是否配置有自定义的init-method
7、BeanPostProcessor后置处理
8、注册必要的Destruction相关的回调接口
9、使用Bean对象
10、是否实现DisposableBean接口
11、是否配置有自定义的destroy方法
```

