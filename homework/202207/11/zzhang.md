MESI协议是基于INvalidate的高速缓存一致性协议，并且是支持回写高速缓存最常用的协议之一。

为什么需要缓存一致性协议？ 现代CPU为多核的情况下，每个核心有自己单独的缓存区，当多个核心操作多个线程对同一个数据进行操作数会导致数据的不确定性。

加锁可以解决这种情况，但是加锁会导致严重的性能下降。缓存一致性写只会对单个的缓存行进行类似加锁的操作，来尽可能的保证数据的一致性。
