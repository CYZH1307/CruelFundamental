## 什么是装饰器模式？ 用你喜欢的语言举一个例子

装饰器模式允许向一个现有对象添加新的功能同时又不改变其结构，是作为现有类的一个包装。

```cpp

class IBeauty {
    public:
        int GetBeauty();
};
class Beauty: public IBeauty {
public:
    int GetBeauty() { return 100;}
};

class Ring: public IBeauty {
    public:
        Ring(IBeauty * ptr) {
       this->ptr = ptr;}
        int GetBeauty(){
        return ptr -> GetBeauty() + 20;
        }
    private:
        IBeauty *ptr;
};
```
