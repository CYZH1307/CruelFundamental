> 为什么会出现tcp粘包？如何解决？

所谓的tcp粘包可以理解为，在client侧send了两次数据，在server侧通过一次recv就获得了全部内容。看起来好像分两次发送的数据粘在了一起。

本质上，tcp中数据是以byte stream的方式进行send/recv的。本身就不具有udp的packet的概念，因此自然两次发送的数据不会自然分开。

要解决粘包，本质上是要设计好application层的protocol

对于HTTP/SMTP这类协议来说，其header的格式是固定的，并且对于payload的长度也明确，因此实现中可以根据数据长度读出需要的字节数，并作为一个整体交给更上层的应用代码使用。

对于自定义的protocol，也是类似。

```cpp
class MyProtocol {
    int msg_type;
    int msg_len;
    char* data;
};
```
