Q: main函数原理
A: 
在有操作系统的环境中，c语言需要main函数
在单片机或者操作系统底层c入口，是不需要main函数的
链接器把main函数和启动程序链接，使main看起来是程序入口
main函数是默认约定入口，如果没实现，会报错
可以加-e：gcc hello.c -e mymain -nostartfiles来自定义启动接口
