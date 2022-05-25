# Linux下进程间通信方法

Reference: https://tldp.org/LDP/tlk/ipc/ipc.html, https://cloud.tencent.com/developer/article/1690556, https://www.cnblogs.com/xiaolincoding/p/13402297.html

 

1. Signal

- 唯一的异步通信机制。

- 看系统支持的Signal: `kill -l`

  ```
  HUP INT QUIT ILL TRAP ABRT EMT FPE KILL BUS SEGV SYS PIPE ALRM TERM URG STOP TSTP CONT CHLD TTIN TTOU IO XCPU XFSZ VTALRM PROF WINCH INFO USR1 USR2
  ```

- 并非系统中的每个进程都可以向每个其他进程发送信号，kernel 和 super users 可以。普通进程只能向具有相同 *uid* 和 *gid* 的进程或同一进程组的进程发送信号。
- 信号处理方式：1）执行默认操作，2）捕捉信号，3）忽略信号



2. Pipes

- Implemented using two *file* data structure. One for writing to the pipe, the other for reading from the pipe. Use shared data page to implement.
- The reader and the writer of the pipe are in step and to do this it uses locks, wait queues and signals.

- Sleep on wait queue if the pipe is locked by the reader or if there is no enough room for the data.
- 匿名管道：通信范围是存在父子关系的进程
- 命名管道：都可以



3. Shared memory

- 映射一段能被其他进程访问的内存。
- Access to shared memory areas is controlled via keys and access rights checking
- Once the memory is being shared, there are **no** checks on how the processes are using it. They must rely on other mechanisms, for example semaphores, to synchronize access to the memory.



4. Message Queues

- 消息队列是保存在内核中的消息链表，在发送数据时，会分成一个一个独立的数据块。支持多个进程读，多个进程写。

- 克服了Signal传递信息少、Pipe只能承载无格式字节流以及缓冲区大小受限等缺点。

- 存在用户态与内核态之间的数据拷贝开销。不适合比较大数据的传输。

  - `MSGMAX` 一条消息的最大长度
  - `MSGMNB` 一个队列的最大长度

  

5. Semaphores

- 信号量是一个计数器，可以用来控制多个进程对共享资源的访问。防止某进程正在访问共享资源时，其他进程也访问该资源。
- 作为进程间以及同一进程内不同线程之间的同步手段。



6. Socket 

- 主要用于跨机通信。也可用于主机上进程间通信。

- 创建Socket

  ```c++
  int socket(int domain, int type, int protocal)
  ```

  
