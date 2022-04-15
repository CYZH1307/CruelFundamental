1. 进行域名解析：通过DNS协议把域名转换为IP地址

2. 拿到IP后，TCP三次握手建立连接。

3. 通过TCP发送HTTP请求

浏览器向服务器发送HTTP请求：
GET /example/example.html HTTP/1.1
浏览器发送请求的头信息：例如USER-AGENT 最后发送一个空请求代表请求头信息发送完毕

服务器应答：首先返回协议版本号和状态码：HTTP/1.1 200 OK，再发送应答的头信息，最后发送数据

HTTP1.0中，发送完毕之后即关闭TCP连接。
HTTP1.0 短连接 每次传输都要建立TCP连接并关闭。多次传输则需要多次建立和关闭

HTTP1.1 支持长连接和请求的流水线处理，在一个TCP连接上可以传送多个HTTP请求和响应
HTTP1.1中 由keep-alive参数控制