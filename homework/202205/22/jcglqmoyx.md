A process is a running activity of a program with a certain independent function on a certain data set, and a process is
an independent unit of the system for resource allocation and scheduling. Each process has its own independent memory
space, and different processes communicate through inter-process communication. Since the process is relatively heavy
and occupies independent memory, the switching overhead (stack, register, virtual memory, file handle, etc.) between
context processes is relatively large, but it is relatively stable and safe.
***
A thread is an entity of a process and the basic unit of CPU scheduling and dispatch. It is a basic unit that is smaller
than a process and can run independently. The thread itself basically does not own system resources, only a few
resources that are essential in operation (Such as program counter, a set of registers and stack), but it can share all
resources owned by the process with other threads belonging to the same process. Inter-thread communication is mainly
through shared memory, context switching is fast, and resource overhead is less, but compared to the process, it is not
stable enough to easily lose data.
***
The coroutine is a lightweight thread in user mode, and the scheduling of the coroutine is completely controlled by the
user. The coroutine has its own register context and stack. When the coroutine is scheduled to switch, save the register
context and stack to other places. When switching back, restore the previously saved register context and stack.
Directly operating the stack will basically have no core switching overhead, and you can access global variables without
locking. , So the context switching is very fast.

