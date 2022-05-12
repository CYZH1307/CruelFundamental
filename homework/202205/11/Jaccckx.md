# vector与list

vector的底层本质上是由三个迭代器组成，通过这三个迭代器，我们可以完成一系列我们想要的操作

1. pointer _Myfirst;
2. pointer _Mylast;
3. pointer _Myend;



list相对复杂， 但本质也是双向链表



对于节点的定义

```cpp
template<typename T,...>
struct __List_node{
    //...
    __list_node<T>* prev;
    __list_node<T>* next;
    T myval;
    //...
}
```

迭代器的定义

```
template<tyepname T,...>
struct __list_iterator{
    __list_node<T>* node;
    //...
    //重载 == 运算符
    bool operator==(const __list_iterator& x){return node == x.node;}
    //重载 != 运算符
    bool operator!=(const __list_iterator& x){return node != x.node;}
    //重载 * 运算符，返回引用类型
    T* operator *() const {return *(node).myval;}
    //重载前置 ++ 运算符
    __list_iterator<T>& operator ++(){
        node = (*node).next;
        return *this;
    }
    //重载后置 ++ 运算符
    __list_iterator<T>& operator ++(int){
        __list_iterator<T> tmp = *this;
        ++(*this);
        return tmp;
    }
    //重载前置 -- 运算符
    __list_iterator<T>& operator--(){
        node = (*node).prev;
        return *this;
    }
    //重载后置 -- 运算符
    __list_iterator<T> operator--(int){
        __list_iterator<T> tmp = *this;
        --(*this);
        return tmp;
    }
    //...
}
```

## list容器的底层实现

```
template <class T,...>
class list
{
    //...
    //指向链表的头节点，并不存放数据
    __list_node<T>* node;
    //...以下还有list 容器的构造函数以及很多操作函数
}
```

