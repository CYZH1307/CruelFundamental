## 介绍一下工厂模式的含义及使用场景

工厂模式主要有三类：

- 简单工厂模式
- 工厂方法模式
- 抽象工厂模式

依照产品是具体产品还是具体工程，分为简单工厂模式，和工厂方法模式；根据工厂的抽象程度，可以分为工厂方法模式和抽象工厂模式。

### 简单工厂模式

#### 介绍

创建管理方式很简单，因为仅仅简单的对不同类的对象做了一层封装。该模式通过向工厂传递类型，在指定要创建的对象。

![image-20220217133118437](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220217133118437.png)

以生产不同车为例：FuleCar and ElectricCar

Car类：不同类型的车(AbstractProduct)

```java
public interface Car {
	void make();
}
```

FuelCar类：制造燃油车(Product1)

```java
public class FuleCar implements Car {
    public FuleCar() {
        this.make();
    }
    @Override
    public void make() {
        System.out.println("make a fule car.");
    }
}
```

ElectricCar类：制造电车(Product2)

```java
public class ElectricCar implements Car {
    public ElectricCar() {
        this.make();
    }
    @Override
    public void make() {
 		System.out.println("make a electric car.");       
    }
}
```

CarFactory类：车代工厂(Factory)

```java
public class CarFactory {
    public Car makeCar(String carType) {
        // 不考虑大小写区别
        if(carType.equalsIgnoreCase("FuleCar")) return new FuleCar();
        else if(carType.equalsIgnoreCase("ElectricCar")) return new ElectricCar();
        return null;
    }
}
```



```java
public class Demo {
	public static void main(String[] args) {
		CarFactory factory = new CarFactory();
        Car fuelCar = factory.makeCar("fuelCar");	// make a fule car.
        Car eleCar = factory.makeCar("electricCar");	// make a electric car.
	}
}
```



#### 适用场景

工程类负责创建对象较少，客户只知道传入的工程厂类的参数，对于如何创建对象不关心。

#### 优缺点

- 优点：可以决定在什么时候创建那个产品类的实例，简单工程依据外界给的信息决定具体创建类的对象。
- 缺点：复杂多层级结构，工厂集中产品创建逻辑，一旦增加产品或者删除产品，整个系统都要受到影响。扩展困难。



### 工厂方法模式

#### 介绍

Factory Method

和简单工厂中，工厂负责生产所有的产品相比，工厂方法模式，将生产具体产品的任务分发给具体的产品工厂。UML如下：

![image-20220217112832263](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220217112832263.png)

定义一个抽象工程，定义了产品的生产接口，但是不负责具体的产品生产，而是将生产任务交给不同的派生工厂，这样不用通过指定类型来创建对象。

Car类：不同类型的车(AbstractProduct)

```java
public interface Car {
	void make();
}
```

FuleCar类：制造燃油车(Product1)

```java
public class FuleCar implements Car {
    public FuleCar() {
        this.make();
    }
    @Override
    public void make() {
        System.out.println("make a fule car.");
    }
}
```

ElectricCar类：制造电车(Product2)

```java
public class ElectricCar implements Car {
    public ElectricCar() {
        this.make();
    }
    @Override
    public void make() {
 		System.out.println("make a electric car.");       
    }
}
```



AbstrictFactory类：生产不同产品的工厂的抽象类。

```java
public interface AbstractFactory {
    Car makeCar();
}
```

FuelCarFactory类：生产电车的工程(ConcreteFactory1)

```java
public class FuelCarFactory implements AbstractFactory {
    @Override
    public Car makeCar() {
        // 调用构造函数造车
        return new FuelCar();
    }
}
```

ElectricCarFactory类：生产电车的工厂(ConcreteFactory2)

```java
public class ElectricCarFactory implements AbstractFactory {
    @Override
    public Car makeCar() {
        // 调用构造函数造车
        return new ElectricCar();
    }
} 
```

调用:

```java
public class Demo {
    public static void main(String[] arg) {
    	AbstractFactory eleFactroy = new ElectricCarFactory();
        AbstractFactory fuelFactroy = new FuelCarFactory();
        eleFactroy.makeCar();	// make a fule car.
        fuelFactroy.makeCar();	// make a electric car.
    }
}
```



#### 适用场景

当一个类不知道他必须创建对象的类，或者一个类希望子类来指定他所创建的对象，当类将创建的职责，委托给多个帮助子类中的某一个。

#### 优缺点

- 优点：工厂方法为了克服简单工厂扩展困难的问题，有良好扩展性。满足，开放-封闭原则。
- OCP原则：对于功能的扩展开放(可以追加功能)，对于修改是封闭的(已经有的实体不修改的情况下，进行扩展，也即扩展的过程中，不修改老的代码)。
- 缺点：具体产品类的修改，可能需要修改很多类，对工程类的修改很麻烦，每增加一个产品，相应的类也要增加一个子工厂，增加开发量。

### 抽象工厂模式

#### 含义

(Abstract Factory)

简单工厂、工厂方法怎么拆分抽象，都是针对一类的产品Car，如果需要生成另外一种产品Phone，应该怎么表示。

最简单的方式，就是将工厂方法中的模式复制一遍，不过这次用于生产Phone。同时意味着，需要完全复制和修改Car的所有代码，显然不利于扩展和修改。

抽象工厂模式，通过在AbstractFactory中增加创建产品的接口，并且在具体子工厂中实现新加产品的创建，当然前提是子工厂支持生产该产品，否则继承的接口可以什么都不做。

![image-20220217115500327](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220217115500327.png)



演示：

Train类：定义Train产品接口(AbstractPhone)

```java
public interface Train {
	void make();
}
```

FuelTrain类：定义火车

```java
public FuelTrain implement Train {
    public FuelTrain() {
        this.make();
    }
    @Override
    public void make() {
        System.out.println("make fuel train.");
    }
}
```

ElectricTrain类：定义动车

```java
public class ElectricTrain implement Train {
	public ElectricTrain() {
		this.make();
	}
	@Override
	public void make() {
		System.out.println("make electric train.");
	}
}
```

AbstractFactory类：增加Train生产接口

```java
public interface AbstractFactory {
    Car makeCar();
    Train makeTrain();
}
```

FuelVehicleFactory类：增加燃料火车工厂类(ConcreteFactory)

```java
public class FuelVehicleFactory implements AbstractFactory {
    @Override
    public Car makeCar() {
        return new FuelCar();
    }
    @Override
    public Phone makeTrain() {
        return new FuelTrain();
    }
}
```



ElectricVehicleFactory类：增加造动车工厂类

```java
public class ElectricVehicleFactory implements AbstractFactory {
    @Override
    public Car makeCar() {
        // 调用构造函数造车
        return new ElectricCar();
    }
    @Override
    public Phone makeTrain() {
        return new ElectricTrain();
    }
} 
```



演示：

```java
public class Demo {
	public static void main(String[] args) {
		AbstractFactory fuelVehicleFactory = new FuelVehicleFactory();
		AbstractFactory electricVehicleFactory = new ElectricVehicleFactory();
		fuelVehicleFactory.makeCar();
		fuelVehicleFactory.makePhone();
		electricVehicleFactory.makeCar();
		electricVehicleFactory.makePhone();
	}
}
```



#### 适用场景

有产品族，一个工厂内的对象，或多或少有关联，因此能够对关联的产品进行定义，不用重新引入类来管理。对新系列产品可以很好的响应需求，分离了具体的类，系统只消费其中产品族中的一种。



#### 优缺点

优点：适应新系列的产品需求变化。客户和类的实现分离，抽象工厂通过穿甲一个完整的产品系列，降低了模块的耦合。

缺点：难以支持生产新的品种的产品。因为抽象工程是确定了可以被创建的产品集合，支持新种类的产品，需要扩展工程接口，会导致抽象工程类与子类的变化。



小结：

三种应用场景不同，但是都是由于可能封装大量对象和工厂创建，新加的产品需要修改已经定义好的工厂相关的类，因此需要权衡。本质上都是将不变的部分提取出，将可变的部分作为接口，达到复用的目的。



https://www.cnblogs.com/yssjun/p/11102162.html

https://blog.csdn.net/Fly_as_tadpole/article/details/88326807