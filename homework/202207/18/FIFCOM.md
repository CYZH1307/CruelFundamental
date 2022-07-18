#### Linux怎么查询24小时内修改过的文件？ 
使用find查询文件夹中24小时内修改过的文件
```shell
find /target_path -mtime -1 -ls
# 或者更人性化点：
find /target_path -newermt "-24 hours" -ls
```

#### 怎么统计文件中出现次数最多的前10个单词？
```shell
cat data_file | sort | uniq -c | sort -k1,1nr | head -10
```

sort: 排序

uniq -c: 显示唯一的行，并在行首加上本行出现的次数

sort -k1,1nr: 按照第一个字段，数值逆序排序

head -10: 取前10行数据