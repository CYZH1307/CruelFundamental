# Java HashMap实现
- Java 1.7，数组 + 链表
- Java 1.8，数组 + 链表 + 红黑树

## 参数
- DEFAULT_INITIAL_CAPACITY，哈希表数组初始长度2^4
- MAX_CAPACITY，哈希表数组最大长度2^30
- DEFAULT_LOAD_FACTOR，负载因子，默认0.75，实际数量超过容量则双倍扩容
- TREEIFY_THRESHOLD，链表树化阈值，默认为8
- UNTREEIFY_THRESHOLD，红黑树链表化阈值，默认为6
- MIN_TREEIFY_CAPACITY，最小树化阈值，所有元素超过该值才能树化

## 实现
- Entry[] table = new Entry[capacity]
- hash = key.hashCode() % table.length
- table[(n - 1) & hash] = new Node(hash, key, value, null)
- Key1: Entry1 -> Entry2
- Key2：Entry3
