### 说下浏览器请求一个网址的过程?

------

1.DNS解析URL域名得到对应的IP地址
2.根据IP地址与服务器三次握手建立TCP连接(每次建立连接需要从HTTP-TCP-IP-网关以太网头部包装) 建立TCP连接
3.建立连接后，浏览器从第三次握手开始传输请求数据的HTTP报文
4.服务器响应浏览器请求，对应的HTML文本发送给浏览器。
5.浏览器显示数据
6.四次挥手释放TCP