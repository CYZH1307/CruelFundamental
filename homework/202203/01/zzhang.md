### C++：static polymorphism 和 dynamic polymorphism 的区别

- 通过**继承**实现的多态是绑定和**动态**的：

- - 绑定意味着参与多态行为的类型接口是通用基类设计时预先确定的（这个概念的其他术语是扩散的、侵入的）
  - 动态意味着接口绑定在运行时完成（动态）

- 通过**模板**实现的多态是非绑定和**静态**的：

- - 非绑定意味着类型参与多态行为的类型接口不是预先确定的（这个概念其他的术语是非扩散的、非入侵的）
  - 静态意味着接口绑定在编译时完成（静态）



Python 区别 `*args` 和 `**kwargs`

- `args`表示任何多个无名参数，它是一个tuple；
- `kwargs`表示关键字参数，它是一个dict。
- 并且同时使用`args`和`kwargs`时，`args`参数需在`kwargs`前，
  - foo(a=1, b='2', c=3, a', 1, None, )，会提示语法错误`SyntaxError: non-keyword arg after keyword arg”。`



