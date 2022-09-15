## MVC，MVP，MVVM的区别

#### MVC (Model View Controller) 

1. 模型（Model）：数据保存，只负责存储数据，请求数据，更新数据
2. 视图（View）：用户界面，只负责渲染HTML
3. 控制器（Controller）：业务逻辑，负责调度model和view

从大锅烩时代进化，引入了分层的概念，但是层与层之间耦合明显，维护起来不容易

#### MVP （Model View Presenter）

1. 模型（Model）
2. 视图（View）
3. 管理层（Presenter）

在 MVC 基础上进一步解耦，视图层和模型层完全隔离，交互只能通过管理层来进行，问题是更新视图需要管理层手动来进行

#### MVVM （Model View ViewModel）

1. 模型（Model）
2. 视图（View）
3. 视图模型（View-Model）。

MVVM引入双向绑定机制，帮助实现一些更新视图层和模型层的工作，让开发者可以更专注于业务逻辑，相比于之前的模式，可以使用更少的代码量完成更复杂的交互； MVC、MVP、MVVM 模式是我们经常遇到的概念，其中 MVVM 是最常用到的，在实际项目中往往没有严格按照模式的定义来设计的系统，开发中也不一定要纠结自己用的到底是哪个模式，合适的才是最好的。



