http是无状态的，所以需要session和cookie之类的，但是为什么要设计成无状态，而不是一开始就设计为有状态呢



HTTP所谓的“无状态协议”，其实跟Cookies、Session这些都没有什么大的联系，它描述的主要是通信协议层面的问题。

常见的许多[七层协议](https://www.zhihu.com/search?q=七层协议&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A527748675})实际上是有状态的，例如SMTP协议，它的第一条消息必须是HELO，用来握手，在HELO发送之前其他任何命令都是不能发送的；接下来一般要进行AUTH阶段，用来验证用户名和密码；接下来可以发送邮件数据；最后，通过QUIT命令退出。可以看到，在整个传输层上，通信的双方是必须要时刻记住当前连接的状态的，因为不同的状态下能接受的命令是不同的；另外，之前的命令传输的某些数据也必须要记住，可能会对后面的命令产生影响。这种就叫做[有状态的协议](https://www.zhihu.com/search?q=有状态的协议&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A527748675})。

相反，为什么说HTTP是无状态的协议呢？因为它的每个请求都是完全独立的，每个请求包含了处理这个请求所需的完整的数据，发送请求不涉及到状态变更。即使在[HTTP/1.1](https://www.zhihu.com/search?q=HTTP%2F1.1&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A527748675})上，同一个连接允许传输多个HTTP请求的情况下，如果第一个请求出错了，后面的请求一般也能够继续处理（当然，如果导致[协议解析](https://www.zhihu.com/search?q=协议解析&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A527748675})失败、消息分片错误之类的自然是要除外的）可以看出，这种协议的结构是要比有状态的协议更简单的，一般来说实现起来也更简单，不需要使用状态机，一个循环就行了——不过，HTTP本身很复杂，这是另一个故事了，这里暂时不提。

无状态的协议有一些优点，也有一些缺点。

和许多人想象的不同，会话（Session）支持其实并不是一个缺点，反而是[无状态协议](https://www.zhihu.com/search?q=无状态协议&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A527748675})的优点，因为对于有状态协议来说，如果将会话状态与连接绑定在一起，那么如果连接意外断开，整个会话就会丢失，重新连接之后一般需要从头开始（当然这也可以通过吸收无状态协议的某些特点进行改进）；而HTTP这样的无状态协议，使用元数据（如Cookies头）来维护会话，使得会话与连接本身独立起来，这样即使连接断开了，会话状态也不会受到严重伤害，保持会话也不需要保持连接本身。另外，无状态的优点还在于对中间件友好，中间件无需完全理解通信双方的交互过程，只需要能正确分片消息即可，而且中间件可以很方便地将消息在不同的连接上传输而不影响正确性，这就方便了负载均衡等组件的设计。

无状态协议的主要缺点在于，单个请求需要的所有信息都必须要包含在请求中一次发送到服务端，这导致单个消息的结构需要比较复杂，必须能够支持大量[元数据](https://www.zhihu.com/search?q=元数据&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A527748675})，因此HTTP消息的解析要比其他许多协议都要复杂得多。同时，这也导致了相同的数据在多个请求上往往需要反复传输，例如同一个连接上的每个请求都需要传输Host、Authentication、Cookies、Server等往往是完全重复的元数据，一定程度上降低了协议的效率。



话说回来，HTTP协议是无状态协议，这句话本身到底对不对？

实际上，并不全对。HTTP/1.1中有一个Expect: 100-Continue的功能，它是这么工作的：

1. 在发送大量数据的时候，考虑到服务端有可能直接拒收数据，客户端发出[请求头](https://www.zhihu.com/search?q=请求头&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A527748675})并附带Expect: 100-Continue的HTTP头，不发送[请求体](https://www.zhihu.com/search?q=请求体&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A527748675})，先等待服务器响应
2. 服务器收到Expect: 100-Continue的请求，如果允许上传，发送100 Continue的HTTP响应（同一个请求可以有任意个1xx的响应，均不是最后的Response，只起到提示性作用）；如果不允许，例如不允许上传数据，或者数据大小超出限制，直接返回4xx/5xx的错误
3. 客户端收到100 Continue的响应之后，继续上传数据

可以看出，这实际上很明显是一个有状态协议的套路，它需要先进行一次握手，然后再真正发送数据。不过，HTTP协议也规定，如果服务端不进行100 Continue的响应，建议客户端在等待较短的时间之后仍然上传数据，以达成与不支持Expect: 100-Continue功能的服务器的兼容，这样可以算是“能有状态则有状态，否则回到无状态的路上”，这样说HTTP 1.x是无状态的协议也是没错的。

至于HTTP/2，它应该算是一个有状态的协议了（有握手和GOAWAY消息，有类似于TCP的流控），所以以后说“HTTP是无状态的协议”就不太对了，最好说“HTTP 1.x是无状态的协议”



===================================================



关于Cookies和有/无状态的关系，如果把有状态、无状态理解成相同的请求是否返回相同的结果，那么要让某个HTTP服务器有状态，真的需要Cookies吗？不需要对不对？只需要大家都共享一个全局状态就行了，比如说首页上有一个访问计数器“您是第XXXX个访问本页的用户”，每访问一次这个计数器就加1，谁访问都加1，这从刚才的理解来说也是“有状态”，对不对？Cookies/Session的作用是**创建和维护多组独立的状态**，而不是**有状态**。这个状态指的是后端服务的状态，而非[HTTP协议](https://www.zhihu.com/search?q=HTTP协议&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A527748675})本身的状态。所以说，HTTP协议是无状态的协议，这个其实跟服务的状态是无关的。一个服务不管使用何种协议，都可以在服务层面上是有状态的，因为这和通信协议无关，只需要它在响应请求时改变自己的状态即可，例如有一个Shutdown命令可以直接关掉整个服务器不再接受响应，那么它无论如何都是有状态的对吧。所以说，服务本身有没有状态、支不支持会话，其实跟HTTP协议是否有状态是无关的。





有状态意味着状态可持续且可变。如果 HTTP 是有状态的，一个请求发生时可能会导致状态改变，下一个请求发生时它可能要遵循新状态作出不一样的响应。这种情况下，这两个请求不再是独立的，第一个请求是否发生会影响到第二个请求。

无状态意味着 HTTP 这一层是没有记忆的，每一个请求发生时都不需要去回忆过去的请求产生了什么样的记忆，也没办法创造新的记忆去影响将来的请求。这里必须非常明确，无状态的仅仅是 HTTP 这一层，在此之上我们可以搭建有状态的应用层。同时负责多层的服务器也可以是有状态的，只是它在 HTTP 这一层没有状态而已。

这就好比邮政信件是无状态一样，邮局处理每一封信件都是独立的。邮局不会因为来自某个地址的上一封信件没贴够邮票就产生记忆，然后拒绝投递后面所有来自这个地址的信件，直到这个发件地址补够邮资为止。邮局只会拒绝投递邮资不够的那一封信件，这不会影响到其它信件的投递。

尽管信件在邮局这一层是无状态的，写信和收信人之间可以是有状态的。双方可能记得此前的一切通信，可能利用通信之外的其它信息来决定下一封信怎么写。无论是 Cookie 还是其它什么技术手段，在 HTTP 至上搭建有状态的协议并不影响 HTTP 自身无状态。