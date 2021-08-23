# listener pattern

listener pattern 也是 observer pattern.

PUBSUB 的架构就是使用了 observer pattern, 主要是为了解决在一对多的关系下,  一个对象状态改变后, 其他依赖对像可以收到通知, 并做出相应的改变, 而状态改变的对象并不关心最终谁会接受到通知.


## 不同 role

* observer: 希望接收到 notification 的对象们

```
class Observer
{
public:
  virtual ~Observer() {}
  virtual void received_notification(const Entity& entity, Event event) = 0;
};
```

* subject or publisher:
    * 发出 notification
    * 维护一个 observer 的 列表

```
class Publisher
{
public:
    add_observer(Observer* observer);
    remove_observer(Observer* observer);
protect:
    send_notifications();
private:
  Observer* observers[MAX_NUM];
  int num_observers;
};
```


















