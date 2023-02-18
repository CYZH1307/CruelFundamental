# 布隆过滤器

## hash

非常长的2进制向量和一组hash函数

## 算法

1. 有k个hash function
2. insert的话，把k个hash 结果的位置都设置为1
3. check的话，就再hash一下，看每一位是不是都是1

## 应用

主要用来去重, 可能有不准确的情况，适用于对检查重复不特别敏感的地方

- cache 和 code之间，防止缓存穿透（查询大量数据不存在的值，越过cache层，一直查询数据库）
- 爬虫去除重复的网页
- 垃圾邮件

## library

guava, algebird

