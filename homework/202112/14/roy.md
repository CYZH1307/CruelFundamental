# 孤儿进程，僵尸进程

- 孤儿进程：父进程退出的子进程，父进程推出后，子进程由进程1管理，结束后，进程资源由进程1回收。
- 僵尸进程：当子进程结束时，父进程没有回收它的资源，子进程资源仍留在系统中，则子进程成为僵尸进程。

# 写时复制

- Linux中，子进程不会直接复制父进程物理内存中的资源，子进程和父进程公用一片物理内存，但虚拟内存仍然似乎独立的。只有当子进程要更改公用资源的时候，才会复制一份。
- 这建立在子进程会相对少地少修改父进程资源的假设，和节省资源的目的上。

