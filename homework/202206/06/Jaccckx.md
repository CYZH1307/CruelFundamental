● 说说常⻅的Linux命令吧？比如 
● 1)怎么给文件按大小排序
  ○ ls -lS
● 2) 查看日志相关技巧
  ○ tail -n (行数) （目标文件名）
  ○ head -n (行数) (目标文件名）
● 3) 查询一个文件的行数
  ○ find . -name （文件名) | xargs wc -l
● 4) 查询一个文件内出现重复最多的单词
  ○ cat words.txt | sort | uniq -c | sort -k1,1nr | head -10
