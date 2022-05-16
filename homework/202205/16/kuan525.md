####  STL中 unordered_map, unordered_set底层原理是什么？

unordered_map, unordered_set的底层是一个防冗余的哈希表（采用除留余数法）。哈希表最大的优点，就是把数据的存储和查找消耗的时间大大降低，时间复杂度为O(1)；而代价仅仅是消耗比较多的内存。