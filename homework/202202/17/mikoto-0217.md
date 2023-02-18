## 介绍一下工厂模式的含义以及使用场景

工厂模式属于创建型模式，提供了一种创建对象的最佳方式

工厂模式中，创建对象是不会对客户端暴露创建逻辑，而是使用一个共同的接口来指向新创建的对象。

在C++中，可以使用C++多态的特性，将存在继承关系的类，通过一个工厂类创建对应的子类（派生类）对象，项目复杂的情况下，便于子类对象的创建。

工厂模式的实现方式可以分为简单工厂模式，工厂方法模式，抽象工厂模式等。


### 简单工厂模式
简单工厂模式的结构组成：
1. 工厂类： 工厂模式的核心类，会定义一个用于创建指定的的具体实例对象的接口
2. 抽象产品类：是具体产品类的继承的父类或实现的接口。
3. 具体产品类： 工厂类所创建的对象就是此具体产品的实例。

简单工厂模式的特点：
工厂类封装了创建具体产品对象的函数

简单工厂模式的缺陷：
扩展性非常差，新增产品的时候需要修改工厂类

例如：
```
class Shoes {
public:
    virtual ~Shoes() {}
    virtual void show() = 0;
}

class NikeShoes: public Shoes {
public:
    void show() {
        std:: cout << "Just do it!" << endl;
    }
};

class AdidasShoes: public Shoes {
public:
    void show() {
        std:: cout << "Nothing is impossible!" << endl;
    }
};

class LiNingShoes: public Shoes {
public:
    void show() {
        std:: cout << "Everything is possible!" << endl;
    }
};

enum SHOES_TYPE {
    NIKE,
    LINING,
    ADIDAS
};

class ShoesFactory {
public:
    Shoes* CreateShoes(SHOES_TYPE type) {
	switch(type) {
	    case NIKE:
	        return new NikeShoes();
		break;
	    case LINING:
	        return new LiNingShoes();
		break;
	    case ADIDAS:
	        return new AdidasShoes();
		break;
	    default:
	        return NULL;
		break;
	}
    }
}
```
### 工厂方法模式

工厂方法模式的结构组成:
1. 抽象工厂类：工厂方法模式的核心类，提供创建具体产品的接口，由具体工厂类实现。
2. 具体工厂类：继承于抽象工厂，实现创建对应具体产品对象的方式。
3. 抽象产品类：它是具体产品继承的父类（基类）
4. 具体产品类：具体工厂所创建的对象，就是此类。

工厂方法模式的特点：
- 工厂方法模式抽象出了工厂类，提供创建具体产品的接口，交由子类趋势线。
- 工厂方法模式的应用并不只是为了封装具体产品对象的创建，而是要把具体产品对象的创建放到具体工厂类实现。

工厂方法模式的缺陷：
- 没新增一个产品，就要增加一个对应产品的具体工厂类。相比于简单工厂模式而言，工厂方法模式需要更多的类定义。
- 一条生产线只能生产一个产品。


工厂方法模式的实现（在之前基础上）
```
class ShoesFactory {
public:
    virtual Shoes *CreateShoes() = 0;
    virtual ~ShoesFactory() {}
};

class NikeProducer: public ShoesFactory
{
public:
    Shoes* CreateShoes() {
	return new NikeShoes();
    }
};

class AdidasProduce: public ShoesFactory
{
public:
    Shoes* CreateShoes() {
	return new AdidasShoes();
    }
};

class LiNingProduce: public ShoesFactory
{
public:
    Shoes* CreateShoes() {
	return new LiNingShoes();
    }
};
```

### 抽象工厂模式

抽象工厂模式的结构组成（和工厂方法模式一致）
1. 抽象工厂类： 工厂方法模式的核心类，提供创建具体产品的接口，由具体工厂类实现。
2. 具体工厂类： 继承于抽象工厂，实现创建对应具体产品对象的方式。
3. 抽象产品类： 它是具体产品继承的父类（基类）
4. 具体产品类： 具体工厂所创建的对象，就是此类。

抽象工程模式的特点：
提供一个接口，可以创建多个产品族中的产品对象，例如创建Nike工厂，就可以创建Nike鞋子产品、衣服产品、裤子产品等。

抽象工厂模式的缺陷:
如同工厂方法模式一样，新增产品是，都需要增加一个对应的产品的具体工厂类。

抽象工厂模式的代码：
```
class Clothe {
public:
    virtual void show() = 0;
    virtual void ~Clothe() {}
};

class NikeClothe: public Clothe
{
public:
    void show()
    {
	std::cout << "Nike" << endl;
    }
};

class AdidasClothe: public Clothe
{
public:
    void show() {
	std:: cout << "Adidas Clothe" << endl;
    }
};

class LiNingClothe: public Clothe {
public:
    void show() {
	std:: cout << "LiNing Clothe" << endl;
    }
};

class Factory
{
public:
    virtual Shoes* CreateShoes() = 0;
    virtual Clothe* CreateClothe() = 0;
    virtual ~Factory() {};
};

class NikeProducer: public Shoes
{
public:
    Shoes* CreateShoes() {
	return new NikeShoes();
    }
    Clothe* CreateClothe()
    {
	return new NikeClothe();
    }
};
```

### 总结

以上三种工厂模式，在新增产品时，都存在一定的缺陷。
- 简单工厂模式，需要修改工厂类，违反了开闭法则
- 工厂方法模式和抽象工厂模式，需要增加一个对应产品的具体工厂类，增加代码量。




