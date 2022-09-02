a *pipe* is a method of connecting the *standard output* of one process to the *standard input* of another. Pipes are the eldest of the IPC tools, having been around since the earliest incarnations of the UNIX operating system. They provide a method of one-way communications (hence the term half-duplex) between processes. 	

- Pipe is one-way communication only i.e we can use a pipe such that One process write to the pipe, and the other process reads from the pipe. It opens a pipe, which is an area of main memory that is treated as a ***“virtual file”\***.
- The pipe can be used by the creating process, as well as all its child processes, for reading and writing. One process can write to this “virtual file” or pipe and another related process can read from it.
- If a process tries to read before something is written to the pipe, the process is suspended until something is written.
- The pipe system call finds the first two available positions in the process’s open file table and allocates them for the read and write ends of the pipe.

Advantanges:

1. Unlimited capacity. Two processes can use pipes to transfer as much data as they want, but the file transfer is limited by the size of the computer's hard disk.
2. The suspension mechanism allows one process to wait for another process, and their data transfer and processing are performed concurrently rather than in tandem.