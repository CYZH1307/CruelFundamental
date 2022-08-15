### linux bash 中 `cat input.txt > input.txt` 为什么会导致 input.txt 变为空文件？

I/O重定向cat A > B的过程，需要先准备好输入输出才进行读写操作，在读取文件A的过程，由于> B已经将文件B中的内容都清空了，所以B变为空文件，再读入A。

主要和优先级有关，IO当中stdout和stderr的管道会先准备好，才会从stdin当中读取资料。

例子中 A.txt > B.txt 会先将 B.txt清空掉，然后才会读进A.txt.

因为这里input.txt > input.txt 相当于 input.txt先清空掉，再做input.txt > 这个时候已经被清空了。因此无法读入任何文件，input.txt变为空文件。

因此>表现为"覆盖"的特点，被输入的文件都是被清空一次再读入。>>就是表现为追加了。

