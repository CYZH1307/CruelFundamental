### linux bash 中 `cat input.txt > input.txt` 为什么会导致input.txt变为空文件？

------

因为当前流程是是先清空 `>input.txt` 即重定向文件中的内容，再从 `input.txt` 中获取内容输出。