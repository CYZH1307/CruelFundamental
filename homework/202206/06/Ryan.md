# 说说常⻅的Linux命令吧？比如 1)怎么给文件按大小排序, 2) 查看日志相关技巧, 3) 查询一个文件的行数, 4) 查询一个文件内出现重复最多的单词

 ## 给文件按大小排序：
 从大到小： ls -lS 由小到大：ls -lSr
 ## 查看日志相关技巧：
 如下所示：
    1. tail命令，实时追踪： tail -f [fileName]; 查看最后10行：  tail -n 10 [fileName]
    2. head命令，查看头10行： head -n 10 [fileName]
    3. cat命令，合并文件：cat [fileName1] [fileName2] > [fileName]；追加文件：cat -n [fileName1] > [fileName2] 
    4. sed命令，查找指定行号（%~10行）：sed -n '5,10p' [fileName]；查找指定日期： sed -n '/2014-12-17 16:17:20/,/2014-12-17 16:17:36/p' [fileName]
 
 ## 查询一个文件的行数：
 -wc -l [fileName]
 ## 查询一个文件内出现重复最多的单词：
 cat [fileName] | sort | uniq -c | sort -k1,1nr | head -1
        
        1.sort表示对单词排序
        2.uniq -c 表示显示唯一的行，并打印出该行的重复次数
        3.sort -k1,1nr 表示按照唯一的数字（2.中的重复次数）从大到小排序
        4.head -1 表示打出第1行
