0106 简述http状态码和对应的信息。

http状态码表示http response的类型。由三位十进制数字组成。

1xx 表示需要client继续操作。  
2xx 表示请求成功，比如常见的 200OK。  
3xx 表示重定向。  
4xx 表示client错误。  
5xx 表示server错误。  

比较常见的：

200 OK, 204 No Content, 301 Moved Permanently, 302 Found (Moved temporarily), 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internel Server Error, 502 Bad Gateway


另：RFC的愚人节笑话  
418 I'm a teapot  
超文本咖啡壶控制协议(HTCPCP/1.0)定义了该状态码，表示客户端错误响应代码表示服务器拒绝冲泡咖啡，因为它是个茶壶。


