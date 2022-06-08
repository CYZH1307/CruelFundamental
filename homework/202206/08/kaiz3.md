shm
通过 shmget, shmat, shmdt, shmctl 管理。
可以通过ipcrm命令手动清共享内存。

A shared memory object is only removed after all currently attached processes have detached (shmdt(2)) the object from their virtual address space. – man ipcrm

mmap
通过 mmap, munmap, msync 管理。
mmap会映射到普通文件，所以不仅进程重启还在，机器重启也还在。
