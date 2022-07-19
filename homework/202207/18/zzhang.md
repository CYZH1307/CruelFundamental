查询24小时内修改过的文件： find . -mtime 0

统计文件中出现次数最多的前10个单词 cat words.txt | sort | uniq -c | sort -k1, 1nr | head -10

sort: 对单次排序 uniq -c：显示唯一的行，并在行首加上本行在文件中出现的次数 sort -k1,1nr 按照第一个字段 数值排序 逆序
