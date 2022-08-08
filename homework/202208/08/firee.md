### 什么是 TIME_WAIT 状态，为什么需要 TIME_WAIT 状态？时间是多久，为什么？

tcp 被动(单向通道)断开连接的一方(下面简称 A 方)需要一个 TIME_WAIT，时间是 2MSL. MSL 是 Maximum Segment Lifetime,译为“报文最大生存时间”，可为 30s，1min 或 2min。原因在于 A 方发送的回复消息如果丢失，B 方会反复发送断开请求，这样就能在 2MSL 内检测到，减少 B 方损失，也算是一个君子协定吧。
