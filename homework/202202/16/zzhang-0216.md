介绍一下装饰器模式的含义及使用场景



装饰器 decorator pattern - 结构式模型

- 允许向一个现有的对象添加新的功能，但不改变结构
- 装饰器模式比生成子类更为灵活（继承为类引入静态特征，随着扩展功能增多，子类会很膨胀）



接口

```java
public interface Shape {
  void draw();
}
```



实体类

```java
public class Rectangle implements Shape {
  @Override
  public void draw() {
    System.out.println("Rectangle")
  }
}
```



接口的抽象装饰类 🌟🌟

```java
public abstract class ShapeDecorator implements Shape {
  protected Shape decoratedShape; 
  public ShapeDecorator(Shape decoratedShape) {
    this.decoratedShape = decoratedShape;
  }
  public void draw() {
    decoratedShape.draw()
  }
}
```



扩展ShapeDecorator  类的实体装饰类

```java
public class RedShapeDecorator extends ShapeDecorator {
  public RedShapeDecorator(Shape decoratedShape) {
    super(decoratedShape);
  }
  
  @Override
  public void draw(){
    decoratedShape.draw();
    setRedBorder(decoratedShape);
  }
  
  private void setRedBoarder(Shape decoratedShape) {
    System.out.println("Red Boarder")
  }
}
```



实际使用Decorator：

```java
ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle);
```

