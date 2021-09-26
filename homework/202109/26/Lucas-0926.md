# 程序的生命周期
- https://juejin.cn/post/6844904094671306760

## 编译
- 词法分析，关键字，符号表
- 语法分析，抽象语法书
- 语义分析，类型匹配和转换
- 中间代码，抽象代码，硬件无关
- 目标代码，机器代码，二进制

## 链接
- 目标文件，COFF规范，ELF / Executable LinkableFormat
- file libc-2.27.so
- ELF 包括头部，.text 代码，.data 数据，.bass，字符表，段表
- TODO: ABI version
- readelf -S ab 查看段表
- readelf -s a.o 查看符号表

## 运行
- 可执行文件很大，利用映射把进程需要的快，装进内存
- 如果满了用LRU算法替换
- readelf -l ab 查看程序映射表

