### Linux怎么查询24小时内修改过的文件？ 怎么统计文件中出现次数最多的前10个单词？

------

'Linux怎么查询24小时内修改过的文件？'
find / -mtime 0

'怎么统计文件中出现次数最多的前10个单词？'
cat filename | sort | uniq -c | sort -k1 | tail -10