# 讲讲分页、分段机制

## 分页

操作系统将物理内存分页进行使用. 每页可以有不同的权限, 可读可写可执行. 虚拟地址通过页表和`MMU`关联到真实的物理地址. 一般页的大小为4KB, 如果页过大会存在内部碎片(页内没有完全使用). 页过小会导致页表项增多, 增加页表的开销. 

分页的好处是能进行细粒度的管理和控制.

## 分段

操作系统按照程序的功能划分将内存分成多个段, 比如代码段, 数据段等等. 操作系统提供段表来实现段的访问, 段表包含段基址和段限界. 段的大小可以是不一致的, 这样更加灵活, 不过可能会导致外部碎片.

分段的好处是能够模块化, 不同段提供不同的访问控制, 提高共享性.
