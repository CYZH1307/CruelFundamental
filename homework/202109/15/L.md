## 1. Read and write a file in Linux

Read files is page-based. Linux kernel always transfers whole pages of data at one time. 
When a "read" system call is invoked only to get a small portion of data, if the data is not in RAM,a new page frame space is allocated,
the page is filled with a part of the data file and adds the frame to cache.
It then copies the small portion of the data into the process address space. 

By using a cache buffer, it decreases the amount of IO needed and increased the reading/writing performance.

## 2. Zero copy: mmap & sendfile
Motivation: with traditional IO, there are too many COPY operations and switches of modes.

### Mmap
mmap maps files to kernel buffers with the help of memory mapping.

At the same time, user can share data in kernel space, in order to reduce
the num of copy operations from the kernel to the user space. But no context switch is saved.

### Sendfile
Linux 2.1 provides the sendFile function, in which data does not need to go through user buffer. It enters the Socket Buffer from the kernel buffer. 
Comparing with Mmap, it further saves a context switch.
