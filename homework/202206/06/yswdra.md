### 说说常⻅的Linux命令吧？比如 1)怎么给文件按大小排序, 2) 查看日志相关技巧, 3) 查询一个文件的行数, 4) 查询一个文件内出现重复最多的单词

------

1) du -m * | sort -nr
2) tail -f filename   //能一直显示最新的尾部几行？
3) wc -l filename
4) cat words.txt | sort | uniq -c | sort -k1,1nr | head -1
5) ps -ef | grep xxx //查询包含xxx子串的进程
6) df -h  //查看磁盘容量
7) du -sh //查看当前文件夹所占容量
8) kill -9 pid //立即杀死对应pid的进程
9) find  target_path -name 'filename' //在target_path下查询名称为filename的文件