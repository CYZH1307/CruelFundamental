### 什么是RPC框架？


RPC（Remote Procedure Call Protocol），指远程过程调用，一般用来实现部署在不同机器上的系统之间的方法调用，使得程序能够像访问本地系统资源一样，通过网络传输去访问远端系统资源。

它是一种通过网络从远程计算机程序上请求服务，而不需要了解底层网络技术的思想。RPC 是一种技术思想而非一种规范或协议。

核心 RPC 框架的重要组成：

1、客户端(Client)：服务调用方。

2、客户端存根(Client Stub)：存放服务端地址信息，将客户端的请求参数数据信息打包成网络消息，再通过网络传输发送给服务端。

3、服务端存根(Server Stub)：接收客户端发送过来的请求消息并进行解包，然后再调用本地服务进行处理。

4、服务端(Server)：服务的真正提供者。

5、Network Service：底层传输，可以是 TCP 或 HTTP。


常见的RPC框架：

1、Thrift：

thrift是一个软件框架，用来进行可扩展且跨语言的服务的开发。

它结合了功能强大的软件堆栈和代码生成引擎，以构建在 C++, Java, Python, PHP, Ruby, Erlang, Perl, Haskell, C#, Cocoa, JavaScript, Node.js, Smalltalk, and OCaml 这些编程语言间无缝结合的、高效的服务。

2、Dubbo


3、Spring Cloud
