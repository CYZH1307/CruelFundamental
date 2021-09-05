# 布隆过滤器是什么？有什么用？
- https://mp.weixin.qq.com/s/BfAHWsnQkre4iwOuT-DVsA

# 缓存穿透
- 数据库没有某个值
- 所以缓存里面没有
- 然后查询数据库
- 每次都查询
- 查询请求穿透到数据库
- 给数据库带来压力
- 解决办法：给缓存设置空值或者默认值
- 写入时更新，并设置适当超时
- 或者使用布隆过滤器

## 布隆过滤器
- Request -> HashMap -> Database
- too much memory to store HashMap
- Request -> Bloom Filter -> Database
- 集合A有n个元素，经过k个哈希散列函数，映射到长度为a的数组B的某些位置，并设置为1
- 如果k个位置的标记全部为1，则元素很有可能属于集合A
- 反之，一定不属于A
- 缺点：存在哈希碰撞导致的假阳性
- TODO：本来属于，但测试结果不属于？

## 如何减少误差
- 增加哈希函数数量，减少碰撞的概率
- 增加标记数组的长度
