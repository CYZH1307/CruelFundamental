# HTTP 状态码和对应的信息 



### 1**

服务器收到请求，需要请求者继续执行操作



### 2**

操作被成功接受并处理

200: OK，一般用于GET、POST请求

203: Non-Authoritative Information，非授权信息，请求成功。但返回的meta信息不在原始的服务器，而是一个副本



### 3**

重定向，需要进一步操作

301: Moved Permanently，资源被永久转移到其他URL



### 4**

客户端错误

400: Bad Request， 客户端请求的语法错误，服务器无法理解

401: Unauthorized，请求要求用户的身份认证

404: Not Found，服务器无法根据客户端的请求找到资源

405: Method Not Allowed，客户端请求中的方法被禁止



### 5**

服务器错误

500: Internal Server Error，内部服务器错误

501: Not Implemented，服务器不支持请求的功能

502: Bad Gateway，作为网关或者代理工作的服务器尝试执行请求时，从远程服务器接收到了一个无效的响应

503: Service Unavailable，由于超载或系统维护，服务器暂时的无法处理客户端的请求

