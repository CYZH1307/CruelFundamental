## SpringMVC的启动流程

主要分为几个步骤：

1. Listener 初始化: (class ContextLoaderListener)
   
   （1）初始化创建 Listener

   （2）对于实现了ServletContextListener接口的监听器(class)，执行 listener.contextIntialized() (采用了观察者模式实现)

2. Filter 初始化:
   
   (1) 初始化创建 Filter

   (2) 执行 filter.init()

3. Servlet 初始化: (class DispatcherServlet)

   (1) 初始化创建 Servlet
   
   (2）执行 Servlet.init()