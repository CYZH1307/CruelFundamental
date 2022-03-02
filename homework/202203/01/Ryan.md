# C++：static polymorphism 和 dynamic polymorphism 的区别

无论静态多态和动态多态都是实现多态性质的方式，在函数领域上表现为同名同参数函数在不同子类的重写。区别如下：

## 1.形式：

静态多态中，每个类各自实现同名函数的定义，函数内部逻辑之间可以没有任何关系，只需要声明相同接口并使用模板：

```c++
namespace StaticPoly
{
    class Line
    {
    public:
        void Draw()const{    std::cout << "Line Draw()\n";    }
    };

    class Circle
    {
    public:
        void Draw(const char* name=NULL)const{    std::cout << "Circle Draw()\n";    }
    };

    class Rectangle
    {
    public:
        void Draw(int i = 0)const{    std::cout << "Rectangle Draw()\n";    }
    };

    template<typename Geometry>
    void DrawGeometry(const Geometry& geo)
    {
        geo.Draw();
    }

    template<typename Geometry>
    void DrawGeometry(std::vector<Geometry> vecGeo)
    {
        const size_t size = vecGeo.size();
        for(size_t i = 0; i < size; ++i)
            vecGeo[i].Draw();
    }
}

```

动态多态中，需要一个父类实现虚的基函数，然后各个子类继承该函数并做相应重写。

```c++
namespace DynamicPoly
{
    class Geometry
    {
    public:
        virtual void Draw()const = 0;
    }; // 需要一个抽象的父类

    class Line : public Geometry
    {
    public:
        virtual void Draw()const{    std::cout << "Line Draw()\n";    }
    };

    class Circle : public Geometry
    {
    public:
        virtual void Draw()const{    std::cout << "Circle Draw()\n";    }
    };

    class Rectangle : public Geometry
    {
    public:
        virtual void Draw()const{    std::cout << "Rectangle Draw()\n";    }
    };

    void DrawGeometry(const Geometry *geo)
    {
        geo->Draw();
    }

    //动态多态最吸引人之处在于处理异质对象集合的能力
    void DrawGeometry(std::vector<DynamicPoly::Geometry*> vecGeo)
    {
        const size_t size = vecGeo.size();
        for(size_t i = 0; i < size; ++i)
            vecGeo[i]->Draw();
    }
}
```



## 2.多态实现的阶段：

静态多态在编译时就已经完成，动态多态在运行时通过每个子类的虚表找到相应函数的实现，再完成多态。

## 3.优缺点

两者的优缺点一体两面，列表如下

- 静态多态运行效率较高、允许编译器优化；动态多态则相反。
- 静态多态各函数实现之间松耦合，不需要维护额外的虚表来维持笨重的继承关系；动态多态则相反。
- 静态多态无法高效维护异质对象的共同点，会导致代码重复、调试困难、接口难以复用等问题，不符合面向对象设计的原则；动态多态则可通过修改父类来实现方便维护，避免了以上问题，也更符合直觉认识。
