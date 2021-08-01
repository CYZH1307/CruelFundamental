# Linux命令查询一个文件的行数
- https://github.com/coreutils/coreutils/blob/master/src/wc.c#L285

## 源代码
- 开辟一块缓存，大小为BUFFER_SIZE 
- 用 safe_read 不断安全读入 
- 短行直接检查行尾字符
- 长号用 memchr 检查内存
- 遇到 \n 会将 lines 加 1

## 特殊处理
- 如果缓存块的平均行长度大于 15，就用 memchr 检查内存块
- 这样避免过度的系统调用
- 这个算法在 2015 年的时候对 x86_64 和 ppc64 架构使用
- 但也会在将来重新评估和改进
