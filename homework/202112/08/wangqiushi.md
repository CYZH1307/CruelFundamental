页式管理存在问题：1虚拟地址到物理地址转换慢。2虚拟空间很大时，页表也会变的很大。

为解决上述问题，引入了快表和多级页表。

快表加速了虚拟地址到物理地址的转换速度，提高页表访问速度。快表是一种高速缓冲存储器。采用页表做地址转换，读写内存数据CPU要访问两次，通过快表访问页表会比在内存中访问页表快很多。

快表地址转换流程：按逻辑地址中页号查快表，若存在，则由页架号和单元号形成绝对地址；若不在快表中，则查主存页表，与单元号形成绝对地址，同时将该页登记到快表中。当快表填满要登记新页时，需按照一定策略淘汰旧登记项。

为了提高内存空间性能，提出多级页表。多级页表避免把全部页表一直放在内存中，占用过多空间，不需要的页表不保留在内存中。时间换空间。
