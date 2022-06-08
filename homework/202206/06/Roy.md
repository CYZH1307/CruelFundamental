1) 怎么给文件按大小排序
从大到小：`ls -lS`
由小到大：`ls -lSr`
2) 查看日志相关技巧
场景1: 日志实时监控 `tail -f fdata.log`
场景2：查询最后20行，并且查找关键字**结果** `tail -n 20 fdata.log | grep '结果'`
场景3：查询最后20行，并且查找关键字**结果** `tail -n 20 fdata.log | grep '结果' --color`
场景4：查询最后20行，并且查找关键字**结果**,上下扩展2行 `tail -n 20 fdata.log | grep '结果' --color -a2`
场景5：日志文件超大时，用vim查找

3) 查询一个文件的行数
`wc -l`
4) 查询一个文件内出现重复最多的单词
`cat words.txt | sort | uniq -c | sort -k1,1nr | head -1`
sort:  对单词进行排序
uniq -c:  显示唯一的行，并在每行行首加上本行在文件中出现的次数
sort -k1,1nr:  按照第一个字段，数值排序，且为逆序
head -1:  取前1行数据
