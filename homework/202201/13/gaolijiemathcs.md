# 了解REST API吗？

1、REST简介

（1）REST服务是将资源的状态以最适合客户端或者服务器端的形式，从服务器转移到客户端（或从客户端转移到服务器端）。

（2）REST含义

- 表述性(REpresentational)：REST资源实际上可以用各种形式来表述，需要寻找最适合资源使用者使用的形式，形式可以包括XML、JSON、甚至HTML。
- 状态(State)：使用REST，更关注资源的状态，而不是对资源采取行为。
- 转移(Transfer)：REST涉及到转移资源数据，以某种表述性的形式从一个应用转移到另外一个。

（3）REST行为：

对应HTTP方法：GET、POST、PUT、DELETE、PATCH

CRUD动作：Create(POST)/Read(GET)/Update(PUT or PATCH)/Delete(DELETE)



REST API为客户端暴露了应用的功能。



2、Spring支持REST的方式

- 控制器处理所有HTTP方法
- 借助@PathVariable，控制器处理参数化URL。
- 借助Spring视图和视图解析器，对各种资源进行转换。
- 使用ContentNegotiatingViewResolver进行客户端资源协商，能够在多个视图解析器产生的视图中找到客户端最适合的一种。
- @ResponseBody使用可以绕过视图解析的步骤 ，并且使用信息转换器HttpMethodConverter返回为客户端的响应。
- @RequestBody注解以及HttpMethodConverter实现传入的HTTP控制器转换为控制器处理方法的Java对象。
- RestTemplate中定义了与REST资源交互的方法。



