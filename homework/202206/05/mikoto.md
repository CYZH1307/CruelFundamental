## 讲讲分页和分段机制

### 分段机制
把虚拟地址空间中的虚拟内存组织称一些长度可变的称为段的内存块单元

系统中的所有使用的段都包含在处理器线性地址空间中

### 分页机制
分页机制是在段机制之后进行的，进一步将段线性地址转换为物理地址

分页机制支持虚拟存储技术，在使用虚拟存储的环境中，大容量的线性地址使用小块的物理地址以及某些外部存储空间来模拟
。操作系统通过维护一个页目录和一些页表来管理页面，当程序试图访问线性空间地址中的一个地址位置时，处理器就会使用页目录和页表吧线性地址转换为物理地址，然后在该内存位置上执行所要求的的操作

### 分页和分段的区别

1. 分页机制使用大小固定的内存块，分段管理使用大小可变的块来管理内存。
2. 分页使用固定大小的块，适合管理物理内存，而分段机制大小可变的块更适合处理复杂系统的逻辑分区
3. 段表存储在线性地址空间，而页表保存在物理地址空间



