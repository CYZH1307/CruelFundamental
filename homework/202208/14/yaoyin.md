# linux bash 中 cat input.txt > input.txt 为什么会导致input.txt变为空文件？

Before Linux process the command, Linux needs to settle down input and output. In this command,
it basically said "create a (new) file and put the output of the former part into this file." So
it will clean the input.txt first.