### mvc,mvp,mvvm的区别

- MVC - 最经典的，Model + View + Controller  
  - Model 处理数据库  
  - View 处理显示  
  - Controller 处理逻辑  
- MVP - Model + View + Presenter  
  - 利用 Presenter 替换 Controller  
  - 在 MVP 中 View 并不直接使用 Model，它们之间的通信是通过 Presenter (MVC 中的 Controller) 来进行的，所有的交互都发生在 Presenter 内部，而在 MVC 中 View 会直接从 Model 中读取数据而不是通过 Controller  
- MVVM - Model + View + ViewModel  
  - 利用 ViewModel 替换 Presenter  
  - 基本上与 MVP 模式完全一致，唯一的区别是，它采用双向绑定 (data-binding): View 的 变动，自动反映在 View Model，反之亦然。这样开发者就不用处理接收事件和 View 更新的工作，框架已经帮你做好了  

ref:ImSingee