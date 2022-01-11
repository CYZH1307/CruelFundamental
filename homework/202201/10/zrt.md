0110 简述http与https的区别。简述TLS/SSL, HTTP, HTTPS的关系。

http与https的区别是https在请求过程中的通信套了一层加密。
HTTP为明文通信，HTTPS为加密通信。
HTTPS通信需要一个TLS或者SSL证书，需要向CA申请。一般证书为RSA或椭圆曲线非对称加密。

TLS和SSL都是加密协议，TLS更新一些。

HTTP一般使用80端口，HTTPS一般使用443端口。
