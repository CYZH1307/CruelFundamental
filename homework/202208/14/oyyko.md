```bash
cat input.txt > input.txt
```

在bash中 重定向是先于执行命令完成的。 即 `> input.txt` 先执行  它把文件内容清空 之后再执行前面的命令并把输出写入 

因此：先清空input.txt 再对它做cat 则得到空的文件。