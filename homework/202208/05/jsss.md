# 什么是JWT？

JWT(全称：Json Web Token)是一个开放标准(RFC 7519)，它定义了一种紧凑的、自包含的方式，用于作为JSON对象在各方之间安全地传输信息。该信息可以被验证和信任，因为它是数字签名的。

JWT流程:

1. 用户使用账号、密码登录应用，登录的请求发送到Authentication Server。
2. Authentication Server进行用户验证，然后创建JWT字符串返回给客户端。
3. 客户端请求接口时，在请求头带上JWT。
4. Application Server验证JWT合法性，如果合法则继续调用应用接口返回结果。


## 参考

[知乎Link](https://www.zhihu.com/question/485758060/answer/2257869896)
