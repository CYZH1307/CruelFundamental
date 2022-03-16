## cmake和makefile的区别，简述cmake到可执行文件的过程

make是一个批处理工具，在makefile中用 target: dependency command的形式，指定构建规则，然后调用本机的编译器、链接器生成最终的库和可执行文件。
cmake是一个跨平台的构建工具，相当于在makefile上面抽象了一层，可以生成各个平台对应的`makefile`，检测当前机器的编译器、库等信息，生成符合当前构建环境的命令。

cmake到可执行文件过程：

1. cmake生成当前平台的makefile
2. make按照生成的makefile进行构建
3. 首先用编译器将源文件生成对应的目标文件
4. 然后调用链接器将不同的目标文件放在一起生成对应的可执行文件
