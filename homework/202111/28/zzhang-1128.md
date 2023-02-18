# G1 回收流程及G1适用场景

Reference：https://segmentfault.com/a/1190000020752031

G1将堆分成许多相同大小的区域单元，每个单元称为Region，Region是一块地址连续的内存空间。Region大小一致，为1-32MB。JVM会尽量划分为2048个左右同等大小的region。

### G1 回收流程

1. 初始标记 **[Stop the world]**
   - 标记出GC Root能直接关联到的对象，修改 TAMS (Next Top at Mark Start) 的值，让下一个阶段用户程序并发运行时，能在正确可用的Region中创建新对象。
2. 并发标记 [耗时久，但可与用户程序并行]
   - 从GC Roots开始对堆中对象进行可达性分析，找出存活的对象。
3. 最终标记 [**Stop the world**, parallel]
   - 修正 阶段2中因用户程序继续运行而导致标记产生变动的那一部分标记 记录。JVM将这段时间对象变化记录在线程 Remembered Set logs里，最终标记阶段需要把 Remembered Set logs 里的数据合并到Remembered set里。
     - Remembered set: G1中引入的一种新的能加入根集合的类型。Remembered Sets（也叫RSets）用来跟踪对象引用，通常约占Heap大小的20%或更高。
4. 筛选回收 [可与用户程序并行]
   - 对各个region的回收价值和成本排序，在给定的“停顿”时间线之下，回收一部分region。



### G1 适用场景

- 实时数据占用了超过半数的堆空间 More than 50% of the Java heap is occupied with live data
- 对象分配率或“晋升”的速度变化明显 The rate of object allocation rate or promotion varies significantly
- 期望消除耗时较长的GC或停顿（超过0.5～1秒）Undesired long garbage collection or compaction pauses