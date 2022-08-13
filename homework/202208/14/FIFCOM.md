## `cat input.txt > input.txt` 为什么会导致input.txt变为空文件？

简单来说，`>`的优先级较高，会先执行。

结果就是`>`首先清空(truncate)了`input.txt`，再执行`cat`命令，此时`cat`读取到的就是空文件了，也就无法写入内容到文件