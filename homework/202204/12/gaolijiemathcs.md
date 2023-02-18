### 如何人为避免out-of-order execution

Java中volatile可以防止指令重排序，原理是加了内存屏障。

- LoadLoad屏障，Load1; LoadLoad; Load2 确保Load2之后的指令，要在Load1完成后执行。
- StoreStore屏障：Store1; StoreStore; Store2 确保Store2之前Store1已经完成。
- LoadStore屏障：对于Load1; LoadStore; Store2, 在Store2及后续写入操作之前，保证Load1数据读取完毕
- StoreLoad屏障：对于Store1; StoreLoad; Load2 在Load2之前Store1要完成。

但内存屏障只读写乱序的时候，对读写线程的可见性。