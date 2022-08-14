# linux bash 中 cat input.txt > input.txt 为什么会导致input.txt变为空文件？

重定向操作会先打开文件, 然后执行前面的命令, 将输入重定向到目标文件中. 由于以非追加模式打开了`input.txt`文件, 因此在执行`cat input.txt`时, `input.txt`文件已为空, 所以最终其为空文件.
