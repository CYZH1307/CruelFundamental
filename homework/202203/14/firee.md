#### cmake和makefile的区别, 简述cmake到可执行文件的过程

makefile通常依赖于你当前的编译平台，而且编写makefile的工作量比较大，解决依赖关系时也容易出错。因此，对于大多数项目，应当考虑使用更自动化一些的 cmake或者autotools来生成makefile，而不是上来就动手编写。
cmake跨平台,并可生成 native 编译配置文件,在 Linux/Unix 平台,生成 makefile,在 苹果平台,可以生成 xcode,在 Windows 平台,可以生成 MSVC 的工程文件。

参考：https://blog.csdn.net/qq_28038207/article/details/80791694



cmake 到 可执行文件

cmake命令由CMakeList.txt生成makefile，makefile用make命令可以更有条理的用gcc编译各种源文件，然后就能生成可执行文件了



不错的文章：https://zhuanlan.zhihu.com/p/111110992