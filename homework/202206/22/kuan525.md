### 说下浏览器请求一个网址的过程?

**浏览器从输入网址到渲染页面主要分为以下几个过程**

1. URL 输入
2. DNS 解析
3. 建立 TCP 连接
4. 发送 HTTP / HTTPS 请求（建立 TLS 连接）
5. 服务器响应请求
6. 浏览器解析渲染页面
7. HTTP 请求结束，断开 TCP 连接