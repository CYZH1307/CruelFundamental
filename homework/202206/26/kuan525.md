## 简述DNS劫持

攻击方通过劫持DNS，给你返回一个假的服务器IP（攻击方的服务器），这时候你访问的就不是你要的服务器了，而攻击者为了更好的伪装，往往充当一个“代理”的角色，把你的请求转发给真实的服务器，真实服务器把内容到攻击者，攻击者在这些内容里做些手脚，比如放一些广告，脚本等等，在发送给访问者，访问者看到的内容便是呗改动过的了。

DNS劫持的方式很多，DNS服务器被黑，直接黑进你的电脑里面改hosts文件等等，后者运营商偷鸡摸狗在DNS上搞鬼等等。

### HTTP的安全缺陷
DNS被劫持，根本原因在于HTTP协议本身的一些安全缺陷。
1. 明文传输
2. 不验明身份
3. 没有数据完整性校验

### 解决办法
1. 内容加密
2. 证书
3. 完整性校验

为了解决这三个问题，引入了`HTTPS`