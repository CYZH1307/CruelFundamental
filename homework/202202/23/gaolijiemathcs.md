# 从内存里读一个byte计算机内部是怎么样实现的

CPU给出虚拟地址，通过CPU芯片内部内存管理单元(MMU)的映射关系转换为物理地址，通过物理地址来访问内存。

页表的作用：是驻留在内存中(DRAM)，这个页表=有效位+地址字段(三种情况 物理页号/磁盘地址/null)。有效位为1，代表这个页数据在DRAM内存中的对应物理页地址；有效位为0代表物理页不在内存中，如果有效位为0且后面地址字段为null则这个虚拟也还没分配，如果有效位为0且地址字段不为空，地址位置表示虚拟地址在磁盘(虚拟存储器)上的地址。

![image-20220224144410390](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220224144410390.png)

PTE(Page Table Entry)：页表条目.页表为页表条目数组。

CPU-高速缓存/内存 读取数据的关系。

页表命中的情况下，具体步骤如下

- Step1: CPU处理器给出一个虚拟地址，交给MMU(位于CPU内部)

- Step2: MMU(CPU内部)生成页表地址(PTEA)，去缓存/内存中找对应的页表项(PTE)。
- Step3:  高速缓存/主存向MMU返回找到的页表项(PTE)
- Step4：MMU构造物理地址(虚拟页号+页内偏移---> 物理页号+页内偏移)，传输给高速缓存/主存。
- Step5：高速缓存/主存返回请求的数据给CPU

![image-20220224144437225](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220224144437225.png)

页面命中由硬件处理，如果出现缺页，进行如下：

Step1-3一样。

Step4：PTE有效位为0，MMU触发异常，CPU触发操作系统内核的缺页异常处理程序【页表项没命中缺页中断】。

Step5：缺页异常程序确定内存中页表的牺牲页(可能没有)，如果被替换页面被修改过(脏页)，需要将其换出磁盘。

Step6：缺页异常程序调入新的页面，并且更新存储器页表的PTE。

Step7：缺页处理程序返回原有进程，再次执行导致缺页指令，重新去找页表中的对应项，命中页表，返回数据。

![image-20220224144448138](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220224144448138.png)

高速缓存和内存的关系

注：此处 高速缓存为cache，主存为内存。有设置cache的时候，MMU Step2 去高速缓存找页表地址，如果找到页表，直接返回，没有找到页表则去内存找，并且在cache中缓存页表；Step4用MMU做地址翻译之后去找对应的页表数据，也是先从L1缓存开始找，命中直接返回数据，没有命中则，找到内存返回数据。

![image-20220224144459553](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220224144459553.png)

为了减轻MMU去cache/内存中找对应的PTE，所以引入了快表TLB，TLB位于MMU当中，用于虚拟寻址的缓存，每一行由一个PTE组成。

引入TLB以后步骤：

如果TLB命中情况：

Step1: CPU给一个虚拟地址

Step2:MMU从TLB取出PTE

Step3:MMU翻译虚拟地址为物理地址，发送给缓存/内存中

Step4: 缓存/内存 返回数据给CPU。

![image-20220224144526158](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220224144526158.png)

如果TLB不命中：

MMU需要从L1缓存中取PTE，并且PTE存放在TLB中。(走上面页表的流程)

![image-20220224144533767](https://raw.githubusercontent.com/gaolijiemathcs/image-hosting/master/image-20220224144533767.png)
