- 存在上限  
-  
- 理论堆大小 = 总虚拟地址空间大小 - 内核虚拟内存地址 - 段大小 (.text .data. bss 等) - 栈大小  
-  
- 在 32 位机器上，总虚拟地址空间大小为 4GB，Linux 内核占用为 1GB，留给堆的空间小于 3G  
- 在 64 位机器上，总虚拟地址空间大小为 256TB（只使用了 48 位，2^48 Byte），Linux 内核占用为 128TB，留给堆的空间不到 128TB  
-  
- 另外，除受虚拟空间大小影响外，堆大小还受物理内存影响（实际上是物理内存 + Swap）  
-  
-  
- ## Ref  
	- Why 2^32 addresses mean 2^32 bytes  
		- Because each byte of memory has to have an address.  
			- "Because each byte of memory has to have an address." - in a byte addressable system only, of course. In a word-addressable platform you could have twice the amount of memory..  
		- [Why does a 32-bit OS support 4 GB of RAM？](https://stackoverflow.com/questions/1119278/why-does-a-32-bit-os-support-4-gb-of-ram)  
	- [How a 64-bit process virtual address space is divided in Linux?](https://unix.stackexchange.com/questions/509607/how-a-64-bit-process-virtual-address-space-is-divided-in-linux)  
	- [Linux Kernel - Memory Management](https://www.kernel.org/doc/html/latest/x86/x86_64/mm.html)  
-  