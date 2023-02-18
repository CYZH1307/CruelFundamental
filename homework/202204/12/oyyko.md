- 如何人为避免out-of-order execution

加入fence，即内存屏障

- 完全内存屏障(full memory barrier)保障了早于屏障的内存读写操作的结果提交到内存之后，再执行晚于屏障的读写操作。
- 内存读屏障(read memory barrier)仅确保了内存读操作；
- 内存写屏障(write memory barrier)仅保证了内存写操作。

通过加入内存屏障，保证了一定程度上避免乱序执行。