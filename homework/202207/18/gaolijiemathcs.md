### Linux怎么查询24小时内修改过的文件？ 怎么统计文件中出现次数最多的前10个单词？

1、Linux怎么查询24小时内修改过的文件？

```
// 找fileDir目录下 最近1天修改过的文件
find /fileDir -mtime 1
```

2、怎么统计文件中出现次数最多的前10个单词？

```
cat file.txt | sort | uniq -c | sort -k1,1nr | head -10
```

（1）sort 对单词排序

（2）uniq -c 显示唯一的行 并且在每行行首加上本行在文件中的次数。

（3）sort -k1, 1nr  按照第一个字段，数值排序，并且为逆序

（4）head -10 取前10行数据