介绍一下观察者模式的含义及使用场景



### 观察者模式 Observer Pattern

当一个对象被修改时，会自动通知依赖它的对象。

注意

1. 避免循环引用
2. 如果顺序执行，某一观察者错误会导致系统卡壳，一般采用异步方式



三种类型的类：

1. Subject：带有绑定Observer 到 Client 对象和从 Client 对象解绑Observer的Method
2. Observer：观察者
3. Client



Subject 类：

```java
public class Subject {
  private List<Observer> observers = new ArrayList<Observer>();
  private int state;
  
  public int getState() {return state;}
  
  public void setState(int state) {
    this.state = state;
    notifyAllObservers(); // 🌟
  }
  
  public void attach(Observer observer) {
    observers.add(observer);
  }
  
  public void notifyAllObservers() {
    for (Observer observer: observers) {
      observer.update();
    }
  }
}
```



Observer 的Abstract类:

```java
public abstract class Observer {
  protected Subject subject;
  public abstract void update();
}
```



Observer 实体类（主要区别在Update Method的实现）

```java
public class BinaryObserver extends Observer {
  public BinaryObserver(Subject subject) {
    this.subject = subject
    this.subject.attach(this) // ?
  }
  
  @override
  public void update() {
    System.out.println("Binary String:" + Integer.toBinaryString(subject.getState()))
  }
}
```



---

```java
public class Demo {
  Subject subject = new Subject();
  new BinaryObserver(subject);
  new BinaryObserver(subject);
  subject.setState(10);
  // Binary String: 1010
  // Binary String: 1010
}
```



