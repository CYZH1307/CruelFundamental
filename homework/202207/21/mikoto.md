## 哈希函数处理冲突的方法

1. 开放寻址法

假设我们需要哈希的数量为N 开放寻址法一般需要用大于2N的一个质数作为哈希取模因子，为了减小装载因子，减少冲突。实际遇到冲突时，通常采用线性向后探测空位的方法。即开放寻址

2. 拉链法

拉链法在遇到冲突时，会在冲突位置开一个链表将所有冲突值全部放在链表上，然后线性时间查找。 有的实现中也有用红黑树替换链表提高效率的。当装载因子过大时同样需要再哈希
