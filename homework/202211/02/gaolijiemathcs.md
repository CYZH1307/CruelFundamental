### 请简述 TFO 的设计思路与实现

在TCP的三次握手过程中传输实际有用的数据。它通过握手开始时的 SYN 包中的 TFO cookie 来验证一个之前连接过的客户端。如果验证成功，它可以在三次握手最终的 ACK 包收到之前就开始发送数据，这样便跳过了一个绕路的行为，更在传输开始时就降低了延迟。这个加密的 Cookie 被存储在客户端，在一开始的连接时被设定好。然后每当客户端连接时，这个Cookie被重复返回。

### 请求Fast Open Cookie

1. 客户端发送SYN数据包，该数据包包含Fast Open选项，且该选项的Cookie为空，这表明客户端请求Fast Open Cookie；
2. 支持TCP Fast Open的服务器生成Cookie，并将其置于SYN-ACK数据包中的Fast Open选项以发回客户端；
3. 客户端收到SYN-ACK后，缓存Fast Open选项中的Cookie。

### 实施TCP Fast Open

以下描述假定客户端在此前的TCP连接中已完成请求Fast Open Cookie的过程并存有有效的Fast Open Cookie。

1. 客户端发送SYN数据包，该数据包包含数据（对于非TFO的普通TCP握手过程，SYN数据包中不包含数据）以及此前记录的Cookie；
2. 支持TCP Fast Open的服务器会对收到Cookie进行校验：如果Cookie有效，服务器将在SYN-ACK数据包中对SYN和数据进行确认（Acknowledgement），服务器随后将数据递送至相应的应用程序；否则，服务器将丢弃SYN数据包中包含的数据，且其随后发出的SYN-ACK数据包将仅确认（Acknowledgement）SYN的对应序列号；
3. 如果服务器接受了SYN数据包中的数据，服务器可在握手完成之前发送数据；
4. 客户端将发送ACK确认服务器发回的SYN以及数据，但如果客户端在初始的SYN数据包中发送的数据未被确认，则客户端将重新发送数据；
5. 此后的TCP连接和非TFO的正常情况一致。

### 好处

利用Cookie认证的方式去除一个往返RTT，提前记录Cookie，后面直接复用Cookie进行同一请求节省握手等待的一个RTT，防止第二次请求开始后的SYN-FLOOD攻击。



ref：https://juejin.cn/post/7152904835342270472