## 2022/09/12

### 讲讲HTTP状态码

状态码为3位数字：

1——：指示信息（表示请求已接受，继续处理）

2——：常规（表示请求已被成功接受，理解，接受）

3——：重定向（要完成请求必须进行更近一步的操作）

4——：客户端错误（请求有语法问题或者请求无法实现）

5——：服务器错误（服务器未能实现合法请求）

| 状态码 | 英文描述              | 中文描述                                                     |
| ------ | --------------------- | ------------------------------------------------------------ |
| 100    | Continue              | 客户端继续请求（POST方法）                                   |
| 101    | Switching Protocols   | 切换协议，服务根据客户端的请求切换协议，只能切换到更高的协议 |
| 200    | OK                    | 请求成功（GET，POST方法）                                    |
| 201    | Created               | 已创建，成功请求并且返回了新的资源                           |
| 202    | Accepted              | 已接受，接受请求，但未处理完成                               |
| 301    | Moved Permanently     | 永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新的URI，浏览器会自动定向到新URI。今后任何新的请求都应使用新的URI代替 |
| 302    | Found                 | 临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI |
| 400    | Bad Request           | 客户端请求的语法错误，服务器无法理解                         |
| 404    | Not Found             | 服务器无法找到网页资源                                       |
| 405    | Method Not Allowed    | 客户端请求方法被禁止                                         |
| 500    | Internal Server Error | 服务器内部错误，无法完成请求                                 |
| 502    | Bad Gateway           | 作为网关或者代理工作的服务器尝试执行请求时，从远程服务器收到了一个无效的响应 |
| 504    | Gateway Time-out      | 充当网关或者代理的服务器，未及时从远端服务器获取请求         |
