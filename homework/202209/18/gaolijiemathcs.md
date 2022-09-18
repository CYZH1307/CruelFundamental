### What is RAID structure in OS? What are the different levels of RAID configuration?

1、磁盘冗余阵列（Redundant Array of Independent Disk, RAID）用多个独立的磁盘组成在一起形成一个大的磁盘系统，从而实现比单块磁盘更好的存储性能和更高的可靠性。

2、RAID level：

- RAID0：多个磁盘组合在一起，写数据分为N份写，独立并发写入磁盘，性能高。但不提供冗余备份，磁盘损坏直接丢失。

- RAID1：写磁盘时，同一份数据无差别写入到两份磁盘，工作磁盘和镜像磁盘。冗余备份，但损失空间。

- RAID3：按照RAID0的方式分成多份同时写入，留出一块磁盘做奇偶校验，当出错的时候，可以通过剩下的磁盘恢复。但是预留磁盘要反复读写，易损坏。

- RAID5：不需要单独磁盘写校验码，校验信息分到各个磁盘。校验码也分散到各个磁盘，磁盘损坏从其他磁盘恢复。

  ```
  RAID5校验算法 P = D1 xor D2 xor D3 ... xor Dn  (D1..Dn为数据库, P为校验, xor为异或) 
  https://zhuanlan.zhihu.com/p/80361528
  最少需要3个磁盘来组件磁盘阵列，最多只允许同时损坏1个磁盘。如果2个磁盘损坏无法支持
  ```

- RAID6：改进校验方法，能够双重校验，可以有2个磁盘同时损坏。但是除了同级数据XOR校验区，还需要对每个数据块的XOR校验区。读数据效率高，但是写效率很差。实际很少用。

- RAID10：结合RAID1与RAID0，RAID1来划分2份磁盘，写入数据两份一起写，同时RAID0并发N个文件一起写。能够支持并发写，同时冗余备份。但还是浪费空间。不常用。

https://zhuanlan.zhihu.com/p/51170719