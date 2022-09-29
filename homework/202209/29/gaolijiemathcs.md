### 什么是哈希树(Merkle Tree)？

Merkle Tree，通常也被称作Hash Tree，顾名思义，就是存储hash值的一棵树。Merkle树的叶子是数据块(例如，文件或者文件的集合)的hash值。非叶节点是其对应子节点串联字符串的hash。

1、哈希树是一种树，可以是二叉也可以是多叉树。

2、哈希树的叶子结点的value是数据集合的单元数据或者单元数据Hash

3、非叶子结点的value是根据它下面所有的叶子结点值，然后按照Hash算法计算得出的。



ref:https://developer.aliyun.com/article/842854