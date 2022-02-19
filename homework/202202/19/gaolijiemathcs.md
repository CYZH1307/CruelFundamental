# CPU分几级缓存，大小一般是多少。访问各级缓存需要的时间，需要多少CPU cycle的数量级。

CPU缓存用于减少处理器访问内存所需要的平均时间。当处理器发出内存访问请求的时候，会先查找缓存内是否有请求数据，如果有数据则命中，不用访问内存之间返回数据，不存在(失效)则先将内存中数据存入缓存，再返回处理器。

## CPU分几级缓存大小一般是多少

主流CPU处理器分两级缓存，少数处理器有三级缓存。

一级缓存(L1 Cache)：CPU内核旁，与CPU结合最紧密，一般一级缓存可以分为一级数据缓存和一级指令缓存，分别用于存放数据以及对指令进行即时解码，两者可以同时被CPU访问，减少争用Cache所造成的冲突，提高处理器效能。例如AMD Athlon 为64KB的一级指令缓存和64KB的一级指令缓存。

二级缓存(L2 Cache)：可以看做是一级缓存的缓存，二级缓存用于存储CPU处理时需要用到，但是一级缓存无法存储的数据。二级缓存大小大多在128KB以上。例如256KB

三级缓存(L3 Cache)：可以看做是二级缓存的缓存.多为操作系统多个核共享的，可以看做是一个小型内存。大小将到达MB级别。

![image-20220219131821565](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220219131821565.png)

## 访问各级缓存需要的时间，需要多少CPU cycle的数量级

![image-20220219132157164](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220219132157164.png)



ref:

https://www.cnblogs.com/xuanbjut/p/11608991.html

https://www.cnblogs.com/jokerjason/p/10711022.html