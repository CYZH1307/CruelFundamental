## 原子操作如何实现？

### 但处理器单核系统

保证操作指令序列不被打断即可实现，简单原子操作，cpu提供单条指令，复杂原子操作，需要自旋锁保证、

### 多处理器多核系统

此时原子操作不仅需要spinlock保证，还需要保证不受其他核影响，例如不同核内存空间的冲突，

这种情况下，X86架构中提供了指令前缀LOCK操作，保证指令不受其他核影响。LOCK阻塞其它核对相关内存的访问，


### 补充
现在多数x86处理器 支持CAS的硬件实现，CAS操作阻塞其它核对于相关内存，缓存块的访问等。
