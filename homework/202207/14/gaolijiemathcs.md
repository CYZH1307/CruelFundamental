### Redis 为什么不直接使用 C 字符串，而要自己构建一种字符串抽象类型 SDS(simple dynamic string)？

常数时间获取字符串长度(**C传统字符串长度的时间复杂度为O(N)**)，

预分配空间减少内存分配次数

能够惰性释放空间

保存二进制数据

兼容传统字符串用法。

ref:https://cloud.tencent.com/developer/article/1631794