# 操作系统内存管理

Reference: https://www.tutorialspoint.com/operating_system/os_memory_management.htm, https://blog.nowcoder.net/n/0f0b9b6ae78e4add97b4efe7c4e510e0#1__5



操作系统的内存管理主要是做什么？

- keeps track of each and every memory location, regardless of either it is allocated to some process or it is free.
  - 记录当前Memory使用情况
- tracks whenever some memory gets freed or unallocated and correspondingly it updates the status.
  - 管理Memory的 allocate 和 free

- checks how much memory is to be allocated to processes
  - 管理进程使用Memory使用
- decides which process will get memory at what time
  - Memory使用调度



操作系统的内存管理有哪几种方式?

- Block 块式

  - 每个进程给一块连续内存。存在问题：Framentation
    - External Fragmentation: 分到的内存够，但不连续 => 不能用 => 通过 compaction or shuffle 解决
    - Internal Fragmentation: 分到的内存够且连续，但没用到的部分不能给别的进程用

- Paging 页式

  - Implement virtual memory
  - 把 Process address space 分成 pages (blocks of the same size, 512 B - 8192 B). 按Page分给进程。
  - Main memory 分成 frames (和Page大小一样) . Allocate a frame to a page.
  - Address translation => page map table
    - logical address = page number + page offset
    - physical address = frame number + page offset
  - 使用比实际更多的memory：
    - OS keeps removing idle pages from the main memory and write them onto the secondary memory and bring them back when required by the program

  - 缺点：page table 需要存下来，对小RAM的system不友好

- Segmentation 段式

  - 把 Job (process) 分成不同大小的segment
  - 一个 process 可以分在不连续的memory里，但是同一个segment的需要分配在连续的memory里。

  - 需要 segment map table 和 free memory blocks with segment numbers
    - segment id => starting addr, length



Further: Pageing + Segmentation

- 每个Segmentation本来是variable长度的，且要存在连续memory中 => 可以把 单个segmentation分成不同的page，存在分散的位置上。