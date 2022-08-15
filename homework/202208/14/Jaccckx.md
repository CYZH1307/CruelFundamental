linux bash 中 cat input.txt > input.txt 为什么会导致input.txt变为空文件？
重定向的过程中会提前把目标的文件创建好（为了重定向 stdout/stderr），因此在 cat 程序执行前就已经进行了打开并 truncate 掉 input.txt ，所以 cat 再打开得到的已经是个空文件了
