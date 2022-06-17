虚拟地址
指由程序产生的由段选择符和段内偏移地址组成的地址。
逻辑地址
指由程序产生的段内偏移。有时候直接把逻辑地址当做虚拟地址。
线性地址
指虚拟地址到物理地址变换的中间层，是处理器可寻址的内存空间中的地址。程序代码会产生逻辑地址，也就是段中的偏移地址，加上相应的段基址就成了线性地址。如果开启了分页机制，那么线性地址需要再经过变换，转为为物理地址。如果无分页机制，那么线性地址就是物理地址。
物理地址
指CPU外部地址总线上寻址物理内存的地址信号，是地址变换的最终结果。

作者：saviochen
链接：https://www.jianshu.com/p/8b37d10bc504
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。