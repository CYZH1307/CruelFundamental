unordered_map, unordered_set 的底层是一个防冗余的哈希表（采用除留余数法）

- 查找的时间复杂度为O(1)，但消耗比较多的内存。
- 一般可采用拉链法解决冲突.

拉链法解决冲突：
- 将所有hash相同的node连在同一个linked list中。若选定的hash table size为 m，则可将hash table 定义为一个由 m 个 指针组成的指针数组A[0..m-1]。凡是hash值为i的结点，均插入到以A[i]为head的linked list中。A[i]中初始化为NULL。
