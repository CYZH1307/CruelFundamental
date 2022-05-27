实例化Bean

根据属性，注入需要的Bean

如果Bean实现了BeanNameAware等aware接口，则执行 aware 注入

如果有 BeanPostProcessor，则执行 BeanPostProcessor#postProcessBeforeInitialization 方法

如果 Bean 是 InitializingBean，则执行 afterPropertiesSet 方法

如果有 initMethod ，则执行

如果有 BeanPostProcessor，执行 BeanPostProcessor#postProcessAfterInitialization 方法

使用 Bean

如果 Bean 是 DisposableBean，则执行 destroy 方法

如果有 destroy 方法，则执行