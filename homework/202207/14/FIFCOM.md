#### Redis 为什么不直接使用 C 字符串，而要自己构建一种字符串抽象类型 SDS？

SDS即simple dynamic string
```c
struct SDS {
    // 记录 buf 数组中已使用字节的数量，即字符串长度
    int len;
    // 记录 buf 数组中未使用字节的数量
    int free;
    // 字节数组，用于保存字符串
    char buf[];
};
```

为什么不使用C字符串？
1. 可O(1)复杂度获取字符串长度，cstring需要遍历整个字符串
2. 动态容量，插入字符串前可以根据free防止buf溢出
3. 预分配内存，减少修改字符串长度时内存重分配的次数
4. cstring以`'\0'`作为字符串结尾，sds没有这种限制
5. 在提供更多功能的同时，仍兼容部分cstring函数