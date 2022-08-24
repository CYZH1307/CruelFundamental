# 硬链接软链接区别？硬中断软中断区别？

A symbolic link is like a "shortcut" to the original file. It has its own inode number. If we delete it, original file won't be deleted.

A hard link is like another name of the original file, it has the same inode number, or precisely, there is no difference between original file and hard link, they are all hard links to the same region of disk. Linux will keep the reference count of hard link, when it becomes to 0, the original file will be deleted.

Hardware interrupt:
It is usually related to external hardware devices, such as request to start an I/O. It's used to avoid wasting processor's time.

Software interrupt:
It is usually requested by the processor itself upon executing particular instructions or when certain conditions are met. Every software interrupt signal is associated with a particular interrupt handler. The OS kernal will catch the interrupt and handle it.