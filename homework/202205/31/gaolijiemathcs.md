### Java: 讲一下Spring事务底层是怎么实现的?

Spring事务主要是对数据库事务的基础上进行封装。特点如下：

1、支持原有数据库事务隔离级别

2、加入事务传播概念，提供多个事务的合并或者隔离的功能。

3、提供声明式事务，业务代码和事务分离。

使用事务的方法，Spring提供了3个接口。主要是重写这些方法实现事务。

- TransactionDefinition 事务定义
- TransactionManager 事务管理
- TransactionStatus 事务运行时状态



#### 1、数据库事务隔离级别

| 事务传播行为类型         | 说明                                                         |
| ------------------------ | ------------------------------------------------------------ |
| PROPAGATION_REQUIRED     | 如果当前没有事务就新建一个事务，如果已经存在一个事务，则加入这个事务中。最常见的选择。 |
| PROPAGATION_SUPPORTS     | 支持当前事务，如果当前没有事务，就以非事务方式执行           |
| PROPAGATION_MANDATORY    | 使用当前事务，如果当前没有事务就抛出异常。                   |
| PROPAGATION_REQUIRES_NEW | 新建事务，如果当前存在事务，就把当前事务挂起                 |
| PROPAGATION_NOT_SUPPORT  | 以非事务方式执行本操作，如果当存在事务，就把当前事务挂起。   |
| PROPAGATION_NEVER        | 以非事务方式执行，如果当前存在事务，就抛出异常               |
| PROPAGATION_NESTED       | 如果当前存在事务，则在嵌套事务内执行，如果当前没有事务，则执行PROPAGATION_REQUIRED类似的操作。 |

#### 2、事务传播机制

Spring 在 TransactionDefinition 接口中规定了 7 种类型的事务传播行为。事务传播行为是 Spring 框架独有的事务增强特性，他不属于的事务实际提供方数据库行为。

事务传播行为用来描述由某一个事务传播行为修饰的方法被嵌套进另一个方法的时事务如何传播。

```java
 public void methodA(){
    methodB();
    //doSomething
 }

 @Transaction(Propagation=XXX)
 public void methodB(){
    //doSomething
 }
```

代码中`methodA()`方法嵌套调用了`methodB()`方法，`methodB()`的事务传播行为由`@Transaction(Propagation=XXX)`设置决定。这里需要注意的是`methodA()`并没有开启事务，某一个事务传播行为修饰的方法并不是必须要在开启事务的外围方法中调用。



##### PROPAGATION_REQUIRED

（1）在外围方法未开启事务的情况下`Propagation.REQUIRED`修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。

（2）外围方法开启事务的情况下`Propagation.REQUIRED`修饰的内部方法会加入到外围方法的事务中，所有`Propagation.REQUIRED`修饰的内部方法和外围方法均属于同一事务，只要一个方法回滚，整个事务均回滚。

##### PROPAGATION_REQUIRES_NEW

（1）在外围方法未开启事务的情况下`Propagation.REQUIRES_NEW`修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。

（2）外围方法开启事务的情况下`Propagation.REQUIRES_NEW`修饰的内部方法依然会单独开启独立事务，且与外部方法事务也独立，内部方法之间、内部方法和外部方法事务均相互独立，互不干扰。

##### PROPAGATION_NESTED

（1）在外围方法未开启事务的情况下`Propagation.NESTED`和`Propagation.REQUIRED`作用相同，修饰的内部方法都会新开启自己的事务，且开启的事务相互独立，互不干扰。

（2）外围方法开启事务的情况下`Propagation.NESTED`修饰的内部方法属于外部事务的子事务，外围主事务回滚，子事务一定回滚，而内部子事务可以单独回滚而不影响外围主事务和其他子事务



NESTED 和 REQUIRED 修饰的内部方法都属于外围方法事务，如果外围方法抛出异常，这两种方法的事务都会被回滚。但是 REQUIRED 是加入外围方法事务，所以和外围事务同属于一个事务，一旦 REQUIRED 事务抛出异常被回滚，外围方法事务也将被回滚。而 NESTED 是外围方法的子事务，有单独的保存点，所以 NESTED 方法抛出异常被回滚，不会影响到外围方法的事务。

NESTED 和 REQUIRES_NEW 都可以做到内部方法事务回滚而不影响外围方法事务。但是因为 NESTED 是嵌套事务，所以外围方法回滚之后，作为外围方法事务的子事务也会被回滚。而 REQUIRES_NEW 是通过开启新的事务实现的，内部事务和外围事务是两个事务，外围事务回滚不会影响内部事务。



#### 3、提供声明式事务，业务代码和事务分离。

自己写代码进行开启和提交事务：为编程式事务。

通过注解方式进行使用事务：声明式事务



#### 4、声明式事务底层实现

底层声明式事务是通过代理的方式实现的。Spring中生成代理的方式有两种：JDK动态代理和CGLIB。JDK动态代理只能用于带接口的，CGLIB通过继承方式实现则带不带接口都行。

目标类是我们自己写的，肯定是没有事务的。代理类是系统生成的，对带注解的方法进行事务增强，没有注解的方法原样调用，所以事务是代理类加上去的。

事务是由代理加进去的，所以关键就是代理如何生成。按照上面所说的代理应该具备的特点来看，只能通过继承的方式生成一个子类来充当代理。CGLIB方式实现。并且由于protected package方法 或者 final修饰的 不能被继承，因此只有public方法支持事务。

只要是以代理方式实现的声明式事务，无论是JDK动态代理，还是CGLIB直接写字节码生成代理，都只有public方法上的事务注解才起作用。而且必须在代理类外部调用才行，如果直接在目标类里面调用，事务照样不起作用。



https://mp.weixin.qq.com/s/IglQITCkmx7Lpz60QOW7HA

https://mp.weixin.qq.com/s/JcHt99SAbNIlY063rmylpA