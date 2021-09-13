# CLOSE_WAIT

## 状态定义
CLOSE_WAIT状态是被动关闭的一方收到FIN包后，协议层回复完ACK包后进入的状态。

## 状态流转
1. 主动关闭方调用close，发送FIN至被动关闭方。
2. 被动关闭方收到FIN后回复ACK，进入CLOSE_WAIT状态。此时主动关闭方收到FIN后会进入FIN_WAIT_2；等待被动关闭方的FIN。
3. 被动关闭方在CLOSE_WAIT时主要需要发送各种待发送的数据包；发送完成后，调用close，发送FIN包至主动断开方，等待对方的ACK，进入LAST_ACK。
4. 主动关闭方收到FIN，回复ACK，进入TIME_WAIT；等待2*MSL后结束TIME_WAIT

TIME_WAIT = 2 * MSL 主要的目的是防止老连接的消息被后续的连接误读；其次是为了防止被动断开方没有收到ACK，停留在LAST_ACK，从而该五元组无法在短时间内被连上。

参考链接： https://www.cnblogs.com/kevingrace/p/9988354.html
