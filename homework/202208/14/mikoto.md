## linux bash 中 cat input.txt > input.txt 为什么会导致input.txt变为空文件？


重定向操作中，从实现上来看是先创建/打开目标文件用于重定向标准输入输出，>是直接写入并非追加，因此会清空文件，此时input.txt为空，此时cat就是空了
