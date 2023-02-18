### cmake和makefile的区别，简述cmake到可执行文件的过程

#### cmake和makefile区别

gcc可以理解为语言编译器，程序可以通过gcc编译。但是在多文件下多个用gcc编译容易混乱，所以出现make批处理工具，用于通过Makefile文件中用户指定的命令进行编译与链接。makefile文件中写了用于调用编译器去编译源文件的命令，简单工程可以人工写，复杂工程手写麻烦，更换平台makefile往往要重写，所以出现cmake工具。cmake可以用于跨平台生成makefile给make工具使用。cmake为通过一个动态档CMakeList.txt文件去生成makefile。

#### cmake到可执行文件过程

程序同级路径创建CMakeList.txt => cmake工具生成makefile=>make指令查找makefile并执行=>编译链接得到可执行文件

