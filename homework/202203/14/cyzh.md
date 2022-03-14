## 2022/03/14

### cmake和makefile的区别

#### make工具

一种通过调用mekefile文件的命令来实现编译和链接的工具

#### mekeflie文件

文本文件，包含了make工具中要执行的一系列命令

#### Cmeke工具

功能是生成mekefile文件

### 简述cmake到可执行文件的过程

cmake主要编写CMakeList.txt文件，然后用cmake命令将CMakeList.txt文件转化为mekefile文件，最后使用meke命令生成可执行文件