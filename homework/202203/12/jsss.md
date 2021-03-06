# 讲一下 cache missing rate和降低的方法

由于cpu和内存速度差距过大, 为了解决这种速度差异问题, 一般都是引入缓存. 随着时代的发展进步, 现有系统的缓存结构已经较为复杂, 主要分为L1数据缓存、L1指令缓存、L2缓存、L3缓存, 越快的缓存容量越小价格越高. 一般L1缓存是单核独有, L2和L3是全核共享. 

## 时空局部性

引入缓存的原始是我们编写的程序拥有局部性的特点.

1. 时间局部性. 如果某个数据被访问, 那么再不就的将来它可能被再次访问.
2. 空间局部性. 由于程序存在循环等控制结构, 空间上临近的数据被访问的可能性也就很高了.


## 缓存一致性

多核情况下. 多个核可能都在处理一个缓存数据. 因此必须保证缓存的一致性, 防止出现使用脏数据的情况. 经典的缓存一致性协议是MESI.

1. M(Modify)修改. 独占当前缓存行且该核对其进行了修改.
2. E(Exclusive)独占. 独占当前缓存行.
3. S(Shared)共享. 多个核共享该缓存行, 且无核修改.
4. I(Invalid)无效. 缓存行无效了. 如: 当共享的行被某个核修改后, 其他共享该缓存行的状态即失效了.

cache通过监听总线来检测其他cache来保持多cache之间的缓存数据同步.

## 缓存命中率

cpu的内存请求中, 若请求的内存地址在缓存(cache)中, 则直接从缓存中读入数据. 否则失败, 尝试从内存中读取. 若内存中也没有找到, 就进行缺页中断, 系统将该页`load`进内存和缓存进行读取.

## 提高缓存命中率的方法

1. 使用更好的硬件设备. 设备好, 缓存大, 自然就命中率高.
2. 程序员编写符合时空局部性原理的代码. 充分发挥局部性的优势.   

如c和cpp中, 数组是按行优先存储的. 当我们遍历数组时, 若按行遍历, 则之后将被访问的数据很可能已经被缓存了, 能极大提高缓存命中率. 而按列遍历时, 下一个要访问的数据和上一个访问的数据之间隔了一行, 可能都不在一个缓存行里, 可能会降低缓存命中率.

```cpp
// 按行遍历
for (int i = 1; i <= n; i ++ )
    for (int j = 1; j <= m;  j ++ )
        mat[i][j] = ...
```

```cpp
// 按列遍历
for (int j = 1; j <= m; j ++ )
    for (int i = 1; i <= n;  i ++ )
        mat[i][j] = ...
```