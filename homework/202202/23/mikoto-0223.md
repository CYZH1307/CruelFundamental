## 从内存里读一个byte计算机内部是怎么实现的

1. CPU核发出VA（virtual address）请求读取数据，TLB（translantion lookaside buffer）接收到该地址，TLB是MMU（Memory Managemant Unit）中的一块高速缓存（也是一种cache，是CPU和物理内存之间的cache），缓存最近查找过的VA对应的页表项，如果TLB中缓存了当前VA的页表项就不必去物理内存中读取页表项，否则需要去物理内存中读出页表项保存在TLB中，TLB缓存可以减少访问物理内存的次数。

2. 页表项中不仅保存着物理页面的基地址，还保存着权限和是否cache的标准，MMU首先检查权限位，如果没有访问权限，就引发一个异常给CPU核。然后检查是否允许cache，如果允许cache就气动cache与CPU核互操作。
3. 如果不允许cache，直接发出PA（Physical Address）从物理内存中读取数据到CPU核
4. 如果允许cache，则以VA为缩影到cache中查找是否缓存了要读取的数据，若已缓存（缓存命中）则直接返回给CPU核，若未缓存（缓存未命中）则从物理内存中读取并缓存到cache中。注意的是，cache并不是仅缓存CPU核需要的数据，而是吧相邻数据都读取来缓存，称为cache line
