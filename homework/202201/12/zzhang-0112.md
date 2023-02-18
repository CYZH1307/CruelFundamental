# GET、POST

### GET与Post的区别：

- 请求参数：GET通过URL，多个参数以&连接，POST放在request body中

- 请求缓存：GET请求会被缓存；POST请求不会被缓存，除非手动设置

- 收藏为书签：GET支持，POST不支持

- 安全性：POST比GET安全，GET请求在浏览器执行go back操作时是无害的，而POST会再次请求

- 编码方式：GET只能进行url编码，POST支持多种编码方式

  

### Get方法参数有大小限制吗？

Http Get方法提交的数据大小长度并没有限制，HTTP协议规范没有对URL长度进行限制。这个限制是特定的浏览器及服务器对它的限制。

- Safari：URL最大长度限制为 80,000个字符。

- Chrome：URL最大长度限制为8182个字符。

  

