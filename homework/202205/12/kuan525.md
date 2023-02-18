#### 说说TCP 3次握手的过程和四次挥手的过程。

* **第一次握手：** 建立连接时，客户端发送**SYN包（syn=j）**到服务器，并进入**SYN_SEND**状态，等待服务器确认，SYN：同步序列编号（Synchronize Sequence Numbers）。

* **第二次握手：** 服务器收到 SYN 包，必须确认客户的 **SYN（ack=j+1）**，同时自己也发送一个**SYN包（syn=k）**，即SYN+ACK包，此时服务器进入**SYN_RECV**状态；

* **第三次握手：** 客户端收到服务器的SYN + ACK包，向服务器发送确认包ACK(ack=k+1），此包发送完毕，客户端和服务器进入ESTABLISHED（TCP连接成功）状态，完成三次握手。



- **第一次挥手：** Client发送一个FIN，用来关闭Client到Server的数据传送，Client进入FIN_WAIT_1状态
- **第二次挥手：** Server收到FIN后，发送一个ACK给Client，确认序号为收到序号+1（与SYN相同，一个FIN占用一个序号），Server进入CLOSE_WAIT状态
- **第三次挥手：** Server发送一个FIN，用来关闭Server到Client的数据传送，Server进入LAST_ACK状态
- **第四次挥手：** Client收到FIN后，Client进入**TIME_WAIT状态**，接着发送一个ACK给Server，确认序号为收到序号+1，Server进入CLOSED状态，完成四次挥手