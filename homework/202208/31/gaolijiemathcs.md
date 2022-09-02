### 什么是装饰器模式？用你喜欢的语言实现一个例子。

#### 什么是装饰器模式

功能：动态的给一个对象添加一些额外的职责。

主要解决：解决扩展的过程子类膨胀的问题。

何时使用：不想增加很多子类的情况下扩展类。

如何使用：职责划分，装饰者抽象类继承基础对象，装饰器继承装饰者抽象类。



例如实现饮料的定制化过程：

（1）Beverage饮料类

```java
package Decorator.naichaStoreOrder;

/**
 *  饮料抽象类
 */
public abstract class Beverage {

    public enum Size {TALL, GRANDE, VENTI};

    public enum Sweetness {WHOLE, SEVENTY, THIRTY, TINY};

    public enum Temperature {NORMAL_ICE, REMOVE_ICE, WARM};

    Size size = Size.TALL;

    Sweetness sweetness = Sweetness.SEVENTY;

    Temperature temperature = Temperature.REMOVE_ICE;

    String description = "Unknow Beverage";

    public void setSize(Size size) {
        this.size = size;
    }

    public Size getSize() {
        return this.size;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Temperature getTemperature() {
        return this.temperature;
    }

    public void setSweetness(Sweetness sweetness) {
        this.sweetness = sweetness;
    }

    public Sweetness getSweetness() {
        return this.sweetness;
    }

    public String getDescription() {
        return description;
    }

    public abstract double cost();

}
```



（2）装饰者类

```java
package Decorator.naichaStoreOrder;

import Decorator.simpleStore.Beverage.*;

public abstract class CondimentDecorator extends Beverage {
    public Beverage beverage;

    public abstract String getDescription();

    public Size getSize() {
        return beverage.getSize();
    }
}
```



（3）装饰者

```java
package Decorator.naichaStoreOrder;

public class Hongdou extends CondimentDecorator {
    public Hongdou(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription() + ", Hongdou";
    }

    public double cost() {
        return 4 + beverage.cost();
    }
}
```



（4）基础奶茶

```java
package Decorator.naichaStoreOrder;

public class MochaNaicha extends Beverage {
    public MochaNaicha() {
        description = "MochaNaicha";
    }
    public double cost() {
        return 14;
    }
}
```



（5）商店

```java
package Decorator.naichaStoreOrder;

import Decorator.naichaStoreOrder.Beverage.*;

public class NaichaStore {
    public static void main(String[] args) {
        Beverage beverage = new YuyuanNaicha();
        System.out.println(beverage.getDescription() + " price=" + beverage.cost() + "yuan");

        Beverage beverage3 = new MochaNaicha();
        beverage3.setSize(Size.VENTI);
        beverage3.setSweetness(Sweetness.THIRTY);
        beverage3.setTemperature(Temperature.REMOVE_ICE);
        // 装饰的过程
        beverage3 = new Hongdou(beverage3);
        beverage3 = new Xiancao(beverage3);
        beverage3 = new Yeguo(beverage3);
        // 输出结果
        System.out.println(beverage3.getDescription()
                + ", size=" + beverage3.getSize()
                + ", sweetness=" + beverage3.getSweetness()
                + ", temperatur=" + beverage3.getTemperature()
                + ", price=" + beverage3.cost() + "yuan");

    }
}
```

