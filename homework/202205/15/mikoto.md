## TCP如何保证连接可靠性
1. 数据包校验
2. 失序数据包重排
3. 丢弃重复数据
4. 应答机制，接收方收到数据后发送确认
5. 超时重发，发送方发送数据后启动定时器，未收到确认则重发
6. 流量控制，确保接收方接受数据不会溢出
