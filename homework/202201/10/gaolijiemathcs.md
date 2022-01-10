# 简述http与https的区别，简述TLS/SSL, HTTP, HTTPS的关系。

## http与https区别

|              | http                                                     | https                                                 |
| ------------ | -------------------------------------------------------- | ----------------------------------------------------- |
| 名称         | Hypertext Transfer protocol 超文本传输协议               | HyperText Transfer Protocol Secure 超文本传输安全协议 |
| 信息是否明文 | http信息明文传输                                         | https通过tls/ssl加密传输协议                          |
| 联系         | 不安全明文传输，未经安全加密协议，传输过程中容易被攻击。 | 可以视为HTTP+TLS/SSL协议组成。                        |
| 地址栏       | http://开头                                              | https://开头                                          |
| 默认端口     | 80                                                       | 443                                                   |
| 通信过程     | TCP连接+HTTP通信+关闭TCP连接                             | TCP连接+TLS握手+加密通信+关闭TCP连接                  |

## 简述TLS/SSL, HTTP, HTTPS的关系。

HTTPS不是新的协议，HTTPS=HTTP+加密+认证+完整性保护【HTTP+TLS/SSL】

![image-20220110232112985](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220110232112985.png)

TLS可以看做是安全升级版的SSL。

SSL是用于保证HTTPS用于验证公钥正确性，能够争取将公开秘钥取出，在TCP建立连接后，经过TLS握手，客户端获取到经过验证的公钥，就能使用可靠的公钥进行非对称加密的方式通信，在服务端和客户端进行通信。

但是不是都用HTTPS，因为非对称加密以后会有加密解密的开销，并且服务端去CA机构开证书需要钱。因此不是需要加密的，一般使用HTTP即可。

