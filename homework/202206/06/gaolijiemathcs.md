### 说说常⻅的Linux命令吧？比如 1)怎么给文件按大小排序, 2) 查看日志相关技巧, 3) 查询一个文件的行数, 4) 查询一个文件内出现重复最多的单词

#### （1）怎么给文件按大小排序

- 从大到小排序：ls -Sl 
- 从小到大排序：ls -Slr

#### （2）查看日志技巧

- tail \[必要参数]\[选择参数][文件]

```
命令格式: tail[必要参数][选择参数][文件]
-f 循环读取
-q 不显示处理信息
-v 显示详细的处理信息
-c<数目> 显示的字节数
-n<行数> 显示行数
-q, --quiet, --silent 从不输出给出文件名的首部
-s, --sleep-interval=S 与-f合用,表示在每次反复的间隔休眠S秒

tail  -n  10   test.log   // 查询日志尾部最后10行的日志;
tail  -n +10   test.log   // 查询10行之后的所有日志;
tail  -fn 10   test.log   // 循环实时查看最后1000行记录(最常用的)
tail -fn 1000 test.log | grep '关键字' // 配合grep操作
```

- head

```
跟tail是相反的head是看前多少行日志
head -n  10  test.log   // 查询日志文件中的头10行日志;
head -n -10  test.log   // 查询日志文件除了最后10行的其他所有日志;
```

- cat

```
cat 是由第一行到最后一行连续显示在屏幕上
cat filename // 一次显示整个文件
cat file1 file2 > file  // 将几个文件合并为一个文件  
cat -n textfile1 > textfile2 // 将一个日志文件的内容追加到另外一个 
```

- grep

```
grep 搜索文本内容
grep "match_pattern" file_1 file_2 file_3 ... // 查找匹配
grep "text" -n file_name // 输出包含匹配字符串的行数 -n 选项
```

- sed

```
这个命令可以查找日志文件特定的一段 , 根据时间的一个范围查询，可以按照行号和时间范围查询
// 按照行号
sed -n '5,10p' filename 这样你就可以只查看文件的第5行到第10行。
// 按照时间段
sed -n '/2014-12-17 16:17:20/,/2014-12-17 16:17:36/p'  test.log
```

- more

```
Linux more 命令类似 cat ，不过会以一页一页的形式显示，更方便使用者逐页阅读，而最基本的指令就是按空白键（space）就往下一页显示，按 b 键就会往回（back）一页显示，而且还有搜寻字串的功能（与 vi 相似），使用中的说明文件，请按 h 。
```

- less

```
less 与 more 类似，less 可以随意浏览文件，支持翻页和搜索，支持向上翻页和向下翻页。
ctrl + F - 向前移动一屏
ctrl + B - 向后移动一屏
ctrl + D - 向前移动半屏
ctrl + U - 向后移动半屏
```

ref: https://cloud.tencent.com/developer/article/1579977



#### （3）查询一个文件的行数

wc word count

```
wc -l filename //查看文件里有多少行
wc -w filename //看文件里有多少个word
wc -L filename //文件里最长的那一行是多少个字。
```



#### （4）查询一个文件内出现重复最多的单词

这个语句要求文本一行只能有一个单词。换句话说，一行是看做一个单词的。

先sort排序，然后Uniq -c统计

再用sort 按照统计结果逆序排序

head来找头部的结果。

```
cat words.txt | sort | uniq -c | sort -k1,1nr | head -1
```

```
sort:  对单词进行排序

uniq -c:  显示唯一的行，并在每行行首加上本行在文件中出现的次数

sort -k1,1nr:  按照第一个字段，数值排序，且为逆序

head -10:  取前10行数据
```

