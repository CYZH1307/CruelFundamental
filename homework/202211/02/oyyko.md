### TFO 简介

TFO 是在原始的 TCP 基础上进行扩展的协议，原文在 [RFC7413](https://tools.ietf.org/html/rfc7413) 上，它基于 TCP 的改良之处在于三次握手期间是可以传输应用数据的。虽然在 TCP 中，三次握手包也是可以传输数据，但是 [RFC793](https://tools.ietf.org/html/rfc793#section-3.4) 中说了，即使你传了，也不会将这些数据上传给应用层，所以传了也没用。所以 TFO 就基于此，对 TCP 的三次握手进行了改良，从而提高了网络带宽的使用率，同时，也顺带解决了一些问题。



TFO 的三次握手肯定和 TCP 的三次握手时不同的，但是，过程相似，其中有一些细节需要说明：

首先是整体实现思路，TFO 的整体实现思路为：

1. 先使用普通的 TCP 三次握手建立连接一次，但是这个过程中 TCP Server 会分配一个 cookie 给 Client，Client 将这个 Cookie 保存起来
2. TCP Client 使用 Cookie 构造 TFO 握手包，这个时候正式开始 TFO 的流程
3. TCP Server 需要对 Client 的 Cookie 进行验证，验证通过则正常交流，不通过则忽略

从图中可以看到，第一阶段就是一个普通的三次握手包，但是在请求中多了一个 CookieOpt 的标志；第二阶段的通信从第一个握手包就开始传输数据 DATA_A 了。



### Cookie

TFO 的 Cookie 是用于快速打开的关键，所以有一些限制是需要遵守的：

1. Cookie 的长度必须是偶数，且长度是 0 或者介于 4 ～ 16

   为什么可以是 0？看第一张图的第一阶段，可以发现有一个 CookieOpt = Nil 的普通 TCP 握手包，这个时候是还没有 Cookie 的，所以应该是 0

2. TFO Server 生成 Cookie 需要快速，生成的 Cookie 有时效性

   - 一个简单的实现就是直接将客户端的地址进行 AES-128 加密，然后截断为 64位传回给客户端，下次直接对客户端的 IP 进行同样的操作，然后对比结果即可。
   - 至于时效性的话，可以定时周期得更换 Server 端的 key，这样以前的 Cookie 都失效了

3. TFO Client Cookie 处理

   - TFO 客户端应该将 Cookie 保存下来，如果是多实例的客户端，那么针对每个客户端都应该保存一份
   - TFO Client 尽量将 MSS 也保存下来，这样下次使用的时候第一次就可以知道传多少数据合适，而不用等 ACK 传回 MSS 才知道

### TFO 的用处

从上面的示例中可以看到 TFO 的其中一个优点就是可以提高网络的利用率，尤其是有频繁网络建立的情景，TFO 的优势尤其明显。但是，除此之外，TFO 还有一个很大的优点就是可以防止 [SYN泛洪攻击](https://zh.wikipedia.org/wiki/SYN_flood)。

因为咱们前面说了，TFO 的客户端是需要针对 Client 进行 Cookie 验证的，验证的基础就是时间戳和 IP，如果使用 SYN Flood 攻击，那么就需要使用正确的 Cookie，只要 Cookie 不对，Server 就可以忽略掉这个连接，不会占用到连接队列。当然，这只是其中一个考虑，这个问题比较复杂，先挖个坑，后面专门找个坑来填。





 当前web和web-like应用中一般都是在三次握手后开始数据传输，相比于UDP，多了一个RTT的时延，即使当前很多应用使用长连接来处理这种情况，但是仍然由一定比例的短连接，这额外多出的一个RTT仍然对应用的时延有非常大的影响。TFO就是在这种背景下面提出来的。

TFO(TCP fast open)是TCP协议的experimental update，它允许服务器和客户端在连接建立握手阶段交换数据，从而使应用节省了一个RTT的时延。但是TFO会引起一些问题，因此协议要求TCP实现必须默认禁止TFO。需要在某个服务端口上启用TFO功能的时候需要应用程序显式启用。

二、TFO过程

1.在使用TFO之前，client首先需要通过一个普通的三次握手连接获取FOC(Fast Open Cookie)

- 1.client发送一个带有Fast Open选项的SYN包，同时携带一个空的cookie域来请求一个cookie
- 2.server产生一个cookie，然后通过SYN-ACK包的Fast Open选项来返回给client
- 3.client缓存这个cookie以备将来使用TFO连接的时候使用

2.执行TFO

- 1.client发送一个带有数据的SYN包，同时在Fast Open选项中携带之前通过正常连接获取的cookie
- 2.server验证这个cookie。如果这个cookie是有效的，server会返回SYN-ACK报文，然后这个server把接收到的数据传递给应用层。如果这个cookie是无效的，server会丢掉SYN包中的数据，同时返回一个SYN-ACK包来确认SYN包中的系列号
- 3.如果cookie有效，在连接完成之前server可以给client发送响应数据，携带的数据量受到TCP拥塞控制的限制(RFC5681，后面文章会介绍拥塞控制)。
- 4.client发送ACK包来确认server的SYN和数据，如果client端SYN包中的数据没有被服务器确认，client会在这个ACK包中重传对应的数据
- 4.剩下的连接处理就类似正常的TCP连接了，client一旦获取到FOC，可以重复Fast Open直到cookie过期。



​    通过整个过程，我们可以看到TFO的核心是一个安全cookie，服务器使用这个cookie来给客户端鉴权。一般来说这个cookie应该能鉴权SYN包中的源IP地址，并且不能被第三方伪造。为了保证安全，过一段时间后，server应该expire之前的cookie，并重新生成cookie。cookie验证通过，server在发送SYN-ACK的时候，如果有待发送数据也同样可以携带数据。

   client在缓存cookie的时候，协议同样建议缓存Maximum Segment Size(MSS)，MSS代表了对端能接收的最大TCP段，这样client在执行TFO的时候，SYN包可以携带的数据量大小就有了一个参考。即使缓存了MSS，也建议client SYN包中数据不要超过典型的MSS，即IPV4的1460bytes和IPV6的1440bytes。如果没有缓存MSS，则SYN包中的数据大小限制在默认的MSS，IPV4为536bytes(RFC1122)，IPV6为1220bytes(RFC2460)。

   client在收到服务器SYN包但是没有ACK之前自己发出的数据时候，或者ICMP错误或者根本没有收到SYN-ACK响应的时候，client至少应该要在对应的连接路径上临时禁止TFO功能。

​    TFO场景下，client在超时重传SYN包以及server超时重传SYN-ACK报文的时候应该去掉Fast Open选项和对应的数据，以免因为不兼容TFO而导致连接建立失败。

三、SYN包重复递交数据

​    在TFO下随着SYN发送的数据有可能重复递交到应用层。例如在IP层不可靠传输的情况下，发送端的一个SYN包被传输成了两个SYN包，而在接收端，接收到第一个SYN包后，接收端把随SYN的数据传递到应用层，紧接着这个连接由发送端发起关闭TCP连接的操作，接收端不会进入TIME_WAIT保护状态(后面在介绍TIME_WAIT)，然后继续收到第二个重复包则可能会将随SYN传输的数据再次传向应用层。因此如果应用层不能忍受这种包重复，则不能开启TFO特性。