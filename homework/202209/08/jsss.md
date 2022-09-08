# UDP传输数据的最大容量？为什么？

IP头 + UDP头 + UDP数据 = 1500 (MTU)
20 Bytes + 8 Bytes + UDP数据 = 1500 Bytes

所以UDP传输最大容量为1472字节.
