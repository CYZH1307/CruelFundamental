## 讲讲HTTP状态码

+ 2xx
  + 200 成功
  + 204 No Content 成功、无响应
+ 1xx
  + 100 Continue 用于表示还有更多数据稍后传输
  + 101 表示切换到 Web Socket
+ 3xx
  + 301 永久重定向 GET
  + 302 临时重定向 GET
  + 307 临时重定向 保留方法
  + 308 永久重定向 保留方法
  + 304 资源未修改
+ 4xx
  + 400 请求不合法
  + 401 未提供认证信息
  + 403 权限不足
  + 404 请求的资源不存在
  + 405 Method Not Allowed
+ 5xx
  + 500 服务器内部错误
  + 502 Bad Gateway
  + 504 Gateway Timeout
  + 503 Service Unavailable
