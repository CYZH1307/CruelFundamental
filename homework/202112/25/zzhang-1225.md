# 会话层、表示层、应用层



会话层：

- Menage user sessions: manage the mapping of messages delivered by the transport layer to the sessions

- 细分为三步：1.建立会话；2.保持会话；3.断开会话。

- 保持会话：当session建立后，开始传递数据，当数据传递完成后，会话层不一定会立即将这个session关闭，可以设置将session保持，session维持期间，用户可以随时使用这个session传递数据。

  

表示层：

- Present data: e.g., encoding: translatition when the underlying layers use a different encoding compared to the one used by the application layer

- Encryption

- End-to-end compression

  

应用层:

- End-user applications