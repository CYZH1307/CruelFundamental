# 请你来说一下socket编程中服务器端和客户端主要用到哪些函数

## 基于TCP的socket编程

- 服务器端

1. 创建一个socket对象, 使用`socket`函数, 指定类型为TCP
2. 使用`bind`函数绑定IP地址和端口信息
3. 使用`listen`函数`backlog`设置阻塞队列长度, 并监听端口
4. 使用`accept`函数服务客户端, 并返回新的套接字用于和客户端通信. 如果无请求或请求超过服务上线, `accept`函数会阻塞.
5. 使用`send`和`recv`函数进行通信
6. 使用`close`系统调用关闭套接字

- 客户端

1. 创建一个socket对象, 使用`socket`函数
2. 使用`connection`函数设定连接的服务器IP和port
3. 使用`send`和`recv`函数进行通信
4. 使用`close`系统调用关闭套接字


## 基于UDP的socket编程

- 服务器端

1. 创建一个socket对象, 使用`socket`函数, 指定类型为UDP
2. 设置网络信息结构体, 使用`bind`函数绑定IP地址和端口信息
3. 使用`recvfrom`函数接收客户端的数据
4. 使用`sendto`函数向服务器主机发送数据
5. 使用`close`系统调用关闭套接字

- 客户端

1. 创建一个socket对象, 使用`socket`函数, 指定类型为UDP
2. 设置服务器地址和端口, 即设置`struct sockaddr`信息
3. 使用`sendto`函数向服务器主机发送数据
4. 使用`recvfrom`函数接收客户端的数据
4. 使用`close`系统调用关闭套接字
