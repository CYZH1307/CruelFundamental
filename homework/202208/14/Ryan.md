# linux bash 中 cat input.txt > input.txt 为什么会导致 input.txt 变为空文件？

因为重定向优先级更高，它吧input.txt写为空文件。
