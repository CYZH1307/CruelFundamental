# C++STL中有哪些容器？分别有什么特点？

## 顺序存储容器
### vector 连续存储，高效随机访问和尾端插入删除，这俩操作均摊O1
### deque 双端队列， 内部类似与多块vector拼接，首尾插删都很快
### list 链表， 非连续存储的双端链表，内存利用率高，但是维护了额外的指针，效率低


## 关联容器

### 无序关联容器

#### unordered_set 无序集合，利用哈希的方式可以将较大的值域映射到一段内存中，用于高效判断元素的存在性，不允许重复元素

#### unordered_map 无序哈希表，常用实现有开放寻址和拉链法，与uunorder_set 不同在于每个键可以对应一个值。

### 有序关联容器

#### set/multiset 有序红黑树集合，前者不允许重复后者允许重复值，常用操作OlogN（但是计算迭代器距离是On，所以也限制了使用）
#### map/multimap 有序红黑树，前者不允许重复键后者允许，感觉multimap没用。
