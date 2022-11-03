## 2022/11/02

### 请简述 TFO 的设计思路与实现

#### TFO(TCP fast open)

- TFO允许服务器和客户端在建立握手阶段交互数据，节省了一个RTT的时延，TCP默认禁止TFO
- TFO过程
  - 客户端发送一个带有Fast Open选项的SYN包，同时带有空的cookie域来请求cookie
  - 服务器端产生cookie，然后通过SYN-ACK包的Fast Open选项返回给客户端
  - 客户端缓存这个cookie
  - 客户端发送带有数据的SYN包，同时在Fast Open选项中携带之前获取的cookie
  - 服务器端验证cookie，返回SYN-ACK报文，如果cookie不正确，服务器端会丢掉SYN包中的数据，同时返回一个SYN-ACK包来确认SYN包中的系列号
  - 如果cookie有效，在连接完成之前服务器端可以给客户端发送响应数据

