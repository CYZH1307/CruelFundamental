### linux bash 中 `cat input.txt > input.txt` 为什么会导致 input.txt 变为空文件？

可以用伪代码简单表示一下
cat input.txt > input.txt 的执行流程相当于

new empty input.txt
重定向 cat 的输出，输出的 fd 从控制台变为 input.txt 的 fd
执行 cat input.txt
