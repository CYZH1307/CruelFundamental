# Linux 文件系统

## 文件描述符
- 文件系统可以分为两个部分去分析: 1.文件如何在硬盘上储存；2.操作系统如何得到文件
- linux文件系统在硬盘上一般可以简单分为5个部分，superblock：文件系统元数据；inode bitmap：显示inode使用情况，datablock bitmap：显示数据块使用情况；inode table； data block table。
- 创建和读取文件都遵循先目录，再文件的顺序，目录也是文件，内部可以简单理解为 {路径，inode} 的哈希表。

## inode 
- inode 是用来指向文件的数据结构，inode里包含文件的元数据， 例如文件大小，所有者，各种权限，时间戳..。
- 多级树状inode可以表示大文件，linux里一级inode一般有12个。

## 文件链接
- 硬链接，inode指向目标文件
- 软链接，inode指向中间文件
