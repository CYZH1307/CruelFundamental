# 策略模式

><https://blog.51cto.com/u_15064711/2984564>

- 在软件开发中，策略模式是为了解决的是策略的切换与扩展，更简洁的说是定义策略族，将每一种策略分别封装起来，让他们之间可以相互替换， 并且不影响原有系统。
- 提供了对“开闭原则”的完美支持，用户可以在不修改原有系统的基础上选择算法（策略），并且可以灵活地增加新的算法（策略）。
- 通俗来说，通常一组class， 他们之间除了行为之外没有其他区别，这样的话我们就可以将他们封装起来，并且动态的让一个对象选择一个行为

## 例子：用策略模式来实现一个收费的不同行为

-声明收费接口

```java
public interface Cash{
    double Calculation(double money);
}
```

-声明上下文类

```java
public class Context {
    private Cash cash;

    public  Context(Cash cash)
    {
        this.cash=cash;
    }

    public  double  ContextShow(double money)
    {
       return cash.Calculation(money);
    }
}
```

-声明正常收费子类

```java
public class CashNormal  implements Cash{
    @Override
    public double Calculation(double money) {
        double total=0;
        total=money;
        System.out.println("正常收费,金额"+total);
        return total;
    }
}
```

-声明打折类

```java
public class CashDiscount implements  Cash{

    private double disCount;
    public  CashDiscount(double disCount)
    {
        this.disCount=disCount;
    }

    @Override
    public double Calculation(double money) {
        double total=0;
        total=money*disCount;
        System.out.println("参加打"+disCount+"折活动,金额"+total);
        return total;
    }
}
```

-声明满减类

```java
public class CashFullreduction implements  Cash{
    //满减金额
    private  double moneyReturn;
    //满减门槛
    private  double moneyConditation;

    public  CashFullreduction(double moneyReturn,double moneyConditation)
    {
        this.moneyConditation=moneyConditation;
        this.moneyReturn=moneyReturn;
    }

    @Override
    public double Calculation(double money) {
        double total=0;
        if(money<300){//如果金额小于300，不满减
            total=money;//加入Java开发交流君样：593142328一起吹水聊天
        }
        else
        {
             total= money-(Math.floor(money/moneyConditation)*moneyReturn);
        }
        System.out.println("支付"+money+"元，满"+moneyConditation+"减"+moneyReturn+",最后金额为"+total);
        return  total;
    }
}
```

-客户端

```java
public static void main(String[] args) {
    Context context=new Context(new CashFullreduction(40,300));//满300-40
    context.ContextShow(400);
    Context context2=new Context(new CashDiscount(0.8));//打八折
    context2.ContextShow(400);
    Context context3=new Context(new CashNormal());//正常收费
    context3.ContextShow(400);
}
```
