# HTTP请求报文内容：报文首部+报文主体

# 请求行 #
包含
    1.请求方法：如GET和POST
    2.请求URL：DNS解析出来的超链接本身
    3.HTTP/HTTPS协议名称及版本。

# 请求头部 #
包含若干个客户端属性，用“propName:propValue”的格式区分，让服务端解析。包括但不限于：

        1.Accept：告诉服务器能够发送哪些媒体类型
        2.Accept-Charset：告诉服务器能够发送哪些字符集
        3.Accept-Encoding：告诉服务器能够发送哪些编码方式
        4.Accept-Language：告诉服务器能够发送哪些语言
        5.Client-IP：提供了运行客户端的机器的 IP 地址
        6.Cookie：客户端用它向服务器传送数据
        7.Expect：允许客户端列出某请求所要求的服务器行为
        8.From：提供了客户端用户的 E-mail 地址
        9.Host：给出了接收请求的服务器的主机名和端口号
        10.Range：如果服务器支持范围请求，就请求资源的指定范围
        11.Referer：提供了包含当前请求 URI 的文档的 URL
        12.UA-Color：提供了与客户端显示器的显示颜色有关的信息
        13.UA-CPU：给出了客户端 CPU 的类型或制造商
        14.UA-OS：给出了运行在客户端机器上的操作系统名称及版本
        15.User-Agent：将发起请求的应用程序名称告知服务器

# 空行 #
用来区分请求头和请求主体

# 请求主体 #
通过“paraName=paraValue”的形式传递请求参数，不同参数之间用&连接。
