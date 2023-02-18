## 基本数据结构

- bit vector
- hash functions: 用来将输入哈希, 结果存入bit vector 中

## 原理

- 当有一个输入 e.g. hello, hash 函数将会对这个字符串进行k次hash 操作, 结果为
    - 0: 1, 6:1 第0位和第6位为1, 将这两位设置到 bit vector 中
    - 下次查询时, 同样的hash 方式, 然后可以从bit vector 中查找这两位是否被设置
    - 如果被设置了, 说明可能存在; 如果没有, 那么一定不存在