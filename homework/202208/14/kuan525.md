### linux bash 中 cat input.txt > input.txt 为什么会导致input.txt变为空文件？

1. `>`优先级比cat高，所以先将后面清空了，前面就为空了
2. `>`会清空文件，并且将stdout设定为文件
3. 当文件不存在时会创建，文件存在时截断（清空）