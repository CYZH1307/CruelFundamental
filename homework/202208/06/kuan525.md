### 什么是基数树(Radix Trie)？

基数树（Radix Trie，也叫基数特里树或压缩前缀树）是一种数据结构，是一种更节省空间的Trie（前缀树）
其中作为唯一子节点的每个节点都与其父节点合并，边既可以表示为元素序列又可以表示为单个元素

和普通tire想不，边可以存字符序列

**查找方式：**
> 基数树查找时节点时，对于节点上的键都按块进行逐块比较

**操作**
> 基数树支持插入、删除、查找操作。查找包括完全匹配、前缀匹配、前驱查找、后继查找。所有这些操作都是O(k)复杂度，其中k是所有字符串中最大的长度。

**结构体**
```go
// 定义节点
type radix_node struct {
	Val    string        `json:"Val"`
	Childs []*radix_node `json:"Childs"`
}

// 定义RadixTree结构体
type RadixTree struct {
	Root *radix_node `json:"Root"`
}
```
[go代码参考链接](https://github.com/ZBIGBEAR/radix_tree)