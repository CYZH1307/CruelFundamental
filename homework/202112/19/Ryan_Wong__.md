# 简述虚拟地址到物理地址的转换

# 段页式存储 #
虚拟地址可分解为 “段号 + 页号 + 页内偏移”

    1.段式转换：按用户逻辑将数据分成大小不一的段（segment），系统维护一个段表，实现段号到 i.段在物理内存上首地址 和 ii.本段的页表 的转换。
    2.页式转换：在每段内分出固定大小的页帧（page frame）以减少内存碎片，每个段维护一个页表，实现页号和页内偏移到实际物理地址的转换。
# 多级页表 #
页的大小如果过大，容易造成碎片增加，内存使用效率变低；页如果过小，又容易造成页表过大，管理开销过大。  
因此，将页表拆分为多级页表可以综合解决该问题。

    i.实现：以二级页表为例，如果页信息相关的虚拟地址有20位，一级页表是这样 “|←   20bits for page table   →|”， 二级页表是这样： “|←  10 for table1  →|←  10 for table2  →|”
    ii.好处1-减少管理内存开销：很明显n级页表的每一层页表大小只是一级页表的1/n，看似总大小几乎不变，  但第一层页表talbe1必然需要申请，第二层或以后的页表需要时才申请，类似于cpp内用二维vector实现邻接图，第二维需要时才申请。
    iii.好处2-提高内存使用效率：可以细化页面大小。
    iv-缺点：多级转换速度慢
    
# 优化1：TLB #
TLB（Translation lookaside buffer）相当于寻址的缓存表，在每次段页式转换过后将符合局部性原理的数据物理地址存入该buffer，则下次访问附近数据时时节省一次段页式转换。
# 优化2：超级页 #
是用来映射虚拟内存中相邻区域的一组相邻页，相当于将相邻的两页打通，这样做的好处是能减少TLB的数量，增加内存使用效率。
