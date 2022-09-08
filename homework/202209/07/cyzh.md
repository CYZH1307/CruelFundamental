## 2022/09/07

### HTTPS和HTTP的区别？

http与https的区别

1. http不加密，https加密
2. 使用https需要到CA（Certificate Authority）申请证书，http不用
3. http页面响应速度比https快，因为http建立连接只需要TCP握手的3个包，而https还需要SSL握手的9个包，一共是12个包
4. http和https采用的是完全不同的连接方式，https的端口是443，http的端口是80.

TLS/SSL 是用来加密明文的，加密后的是https，http不需加密