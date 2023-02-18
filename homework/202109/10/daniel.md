## close wait

- tcp 是全双工的通信协议, 在主动关闭的一方发送fin 后, 被动关闭的一方 收到fin 后
    - 协议层会发送 ack, 然后会进入 close wait
    - 进入close wait 的原因是要等待还在发送的数据发送完成, 然后调用 close 关闭socket
    - 但是有些特殊情况会导致没有调用 close 造成 close_wait
        - 服务器CPU 太忙
        - 服务器应用有bug, 在连接断开后没有close socket
        - 锁等待或者 IO等待

## Epoll 触发方式

在 epoll wait 收割事件时:

- edge
    - 有新消息到, 才会返回, 所以在edge 模式下, 一般都是 while 循环把所有事件都处理掉
- level
    - 只要有事件, epoll wait 就会立马返回