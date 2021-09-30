## Java 的 IO 多路复用
- https://juejin.cn/post/6844903636422623240

## BIO
- while true {
-   Socket socket = server.accept();
-   executor.submit(new Handler(socket));
- }
- BufferReader reader = new BufferReader(new InputStreamReader(socket.getInputStream()));
- while ((s = reader.readLine()) != null) {}

## NIO
- selector.select();
- Iterator it = selector.selectedKeys().iterator();
- while (it.hasNext()) {
-   it.remove();
-   dispatch(it.next());
- }

## 单 Reactor
- SelectionKey key = server.register(selector, SelectionKey.OP_ACCEPT);
- key.attach(new Acceptor())
- 单 Reactor，单线程，多线程
- 多 Reactor，主反应器 附件建立链接，从反应器负责多路复用
