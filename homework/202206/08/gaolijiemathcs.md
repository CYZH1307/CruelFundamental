### mmap 原理和优势，shared memory原理和优势

#### mmap

- 原理：虚拟空间一段区域和磁盘文件之间的映射。
- 优势：相比read/write减少数据拷贝次数，之间从磁盘文件拷贝到内核缓存，可以用在零拷贝上面。

#### shared memory

- 原理：两个进程虚拟空间映射到同一段物理内存，然后可以通信。
- 优势：只要建立了共享内存的关系，不需要额外的复制，进程间通信很快。
