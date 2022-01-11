# HTTP, HTTPS, TLS/SSL

### HTTP与HTTPS的区别

- HTTPS：HTTP over SSL，比HTTP安全。
  - 需要到CA（Certificate Authority）申请证书。
  - HTTPS工作流程：1）TCP三次握手；2）客户端验证服务器数字证书；3）DH算法协商对称加密算法的密钥、hash算法的密钥；4）SSL安全加密隧道协商完成；4）网页以加密的方式传输，用协商的对称加密算法和密钥加密，保证数据机密性；用协商的hash算法进行数据完整性保护，保证数据不被篡改。
- HTTP是超文本传输协议，信息是明文传输的；HTTPS是具有安全性的SSL/TLS加密传输协议
- HTTP使用80端口，HTTPS使用443端口。
- HTTP 页面响应速度比 HTTPS 快： HTTP 使用 TCP 三次握手建立连接，客户端和服务器交换 **3** 个包； HTTPS除了 TCP 的三个包，还要加上 ssl 握手需要的 9 个包，所以一共是 **12** 个包。



### TLS/SSL

- SSL: Secure Socket Layer
- TLS: Transport Layer Security
- TLS比SSL新一点，但一般用SSL指代SSL/TLS

SSL/TLS步骤：

1. 通过CA体系交换public key

2. 通过非对称加密算法，交换用于对称加密的密钥
3. 通过对称加密算法，加密正常的网络通信

CA交换public key: 

- 客户端A和C需要交换public key，但public key又被中间人劫持并伪造的可能。CA来保证public key的真实性。CA也是基于非对称加密算法来工作。
- 用户B向用户A传消息：用户B把把自己的public key（和一些其他信息）交给CA，CA用自己的private key加密这些数据，加密完的数据称为B的**数字证书**。B向A传递public key，B传递的是CA加密之后的数字证书。A收到以后，会通过CA发布的**CA证书**（包含了CA的public key），来解密B的数字证书，从而获得B的public key。
- CA把自己的CA证书集成在了浏览器和操作系统里面。A拿到浏览器或者操作系统的时候，已经有了CA证书，没有必要通过网络获取，自然也不存在劫持的问题。



