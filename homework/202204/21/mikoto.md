# 知道UDP是不可靠的传输，如果你来设计一个基于UDP差不多可靠的算法，怎么设计

- 保序则要求必然要有序列号机制
- 确保不丢包则需要加入确认以及重传机制
- 消息开头设置总大小包头，结尾设置校验
