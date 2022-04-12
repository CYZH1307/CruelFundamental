### 如何人为避免out-of-order execution

------

通过加入内存屏障可以避免，一般有：

- LoadLoad屏障
- LoadStore屏障
- StoreLoad屏障

感觉大致思路就是确保在某个指令前，某些指令必须执行完毕。