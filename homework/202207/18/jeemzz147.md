### Linux怎么查询24小时内修改过的文件？ 怎么统计文件中出现次数最多的前10个单词？

1.find . -mtime 1

2.cat words.txt | sort | uniq -c | sort -k1,1nr | head -10
