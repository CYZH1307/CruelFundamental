## HTTP中get post put和delete的区别

HTTP请求中，定义了与服务器交互的不同的方法，其中最基本的四种：GET，POST，PUT和DELETE

1. GET 和 POST区别
GET把参数包含在URL中，POST通过request body传递参数

GET要比POST快，因为POST需要在额外的描述字段，更重要的是，POST在真正接收数据前会将请求头发送给服务器进行确认，然后才真正发送。

POST请求过程：
1. 浏览器请求TCP链接
2. 服务器应答进行TCP链接
3. 浏览器确认，并发送POST请求头
4. 服务器返回100 Continue响应
5. 浏览器发送数据
6. 服务器返回200 OK响应

GET请求过程
1. 浏览器请求tcp链接（第一次握手）
2. 服务器答应TCP连接（第二次握手）
3。 浏览器确认并发送GET请求头和数据
4. 服务器返回200OK响应

PUT请求时向服务端发送数据，从而改变信息，是用来修改数据的内容

DELETE请求用来删除资源，类比数据库的DELETE操作


