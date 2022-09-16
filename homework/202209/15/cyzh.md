## 2022/09/15

### MVC，MVP，MVVM的区别

#### MVC框架

- Model（模型）表示应用程序核心（如数据库）。
- View（视图）显示效果（HTML页面）。
- Controller（控制器）处理输入（业务逻辑）。

#### MVP模式

- Controller/Presenter负责逻辑的处理

- Model提供数据，

- View负责显示。

#### MVVM框架

mvvm模式将Presener改名为View Model，基本上与MVP模式完全一致，唯一的区别是，它采用双向绑定