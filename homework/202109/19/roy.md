## Kafka如何保证消息有序不丢？

#### 如何保证有序不丢：

Kafka 主要由三个模块构成，分别是broker，consumer，和 producer

broker 会丢失信息主要是因为broker会遵照文件系统的规范，把log存进磁盘中。为了提高存储的效率，broker采取了批量存储的策略，如果当log从内存传输到磁盘中时，broker断电了，log就会丢失。为此kafka采取了
使用ack这一参数，来保证信息的可靠传达与幂等

producer会出问题，也与批处理有关，producer会将log先存在一个buffer中，然后批量发送。当producer被非法终止，或者内存不够时，kafka可能会主动丢弃或被动丢失log

cosumer出问题主要来源于 commit log consumed 与 log 是否被consumed 这两个行为是异步的。如果消费的时候出现异常但已经commit消费成功了，log也可以当成丢失了。
