# 分页机制和分段机制；逻辑地址和物理地址

### 分页机制和分段机制

共同点：

- 都是为了提高内存利用率，产生较少的内存碎片。
- 都是离散分配内存的方式。但是每个页和段中的内存是连续的。

区别点：

- 分页机制：大小固定的块；分段管理：大小不一的块。

- 段表存储在线性地址空间，而页表则保存在物理地址空间

