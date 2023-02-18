## session cookie

实现状态记录，解决HTTP协议无状态的缺陷。

session cookie 都是服务器生成存储特定的值（键值对应）。

session存储在服务器，cookie会返回客户端。

一般SessionID（服务器用来识别、操作存储session值的对象）以类似cookie的方式返回客户端。客户端发送请求的时候，将自动存活可用cookie封装在请求头中和请求一起发送。cookie和session都有生命周期。

cookie生命周期受到两个因素影响：cookie自身存活时间（服务器生成cookie设定）客户端是否保留cookie。

session的生命周期受两个因素：服务器对session对象的保存的最大时间的设置，客户端进程是否关闭。
