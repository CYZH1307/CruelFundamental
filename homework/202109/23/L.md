##  父进程死掉子进程会怎么样？子进程挂掉父进程呢？知道僵尸进程吗，Linux会怎么处理？

#### What will happen to child process if the parent process ends? 
If the parent process is killed, children become children of the *init* process.

Definition of a init process: A process that has the process id 1 and is launched as the first user process by the kernel.

The init process checks periodically for new children, and waits for them.


#### How to deal with zombie process
Definition of a zombie process: We say that a process becomes a zombie when it has completed its task,
but the parent process hasn’t collected its execution status.

1. Send a signal of type SIGCHLD to the parent process asking it to do so.
2. Kill the parent process.

