Redis数据过期后的删除策略？

1. 定时删除：

   - 设置key的过期时间，同时创建定时器，到过期时间立即执行删除操作
   - 内存友好
   - CPU不友好，删除过期件会占用一部分CPU时间

2. 惰性删除

   - key过期后，不真实删除；等需要该key时，检查是否过期，若过期则删除
   - CPU友好，只会在使用该key时才进行检查
   - 内存不友好，若key过期后不被访问，则一直没有删除

3. 定期删除

   - 每隔一段时间，对一些key进行检查，并删除过期的key

   - CPU友好，内存友好

   - trade-off between CPU performance and memory efficiency

     
