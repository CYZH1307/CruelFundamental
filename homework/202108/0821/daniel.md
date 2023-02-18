# visitor pattern

方便实现算法与数据结构的分离

## 何时用
当希望给已有的 数据结构增加一些功能时 (比如给 AST 的不同的节点添加 print 函数), 
* 不能大肆修改其源码
* 或者如果考虑到 solid 里的 single responsibility 原则, 所有的相似的功能最好在一起 而不是分散在各个 data type 各自的类型里
* 不使用运行时类型转化 + if else block 来实现 (维护性)

## how

* 主要的思想是 double dispatch
    * 以 C++ 为例: 一般来讲, 接口里使用多态时, 当传入 基类的指针时, 运行时vtable 会 dispatch 调用到对应的子类的借口
    * double dispatch 就是在 vtable 的基础上, 再加上 function overload 来实现对于不同类型进行不同的操作

伪代码:
```
struct Stuff {
    virtual void accept(Visitor) = 0
}

struct Foo : Stuff {
    void accept(Vistor v) override { v.visit(this) }
}
struct Bar : Stuff {
    void accept(Vistor v) override { v.visit(this) }
}

firstVisitor = new Visitor()
foreach (s in Stuffs) 
	do shape.accept(firstVisitor)
```