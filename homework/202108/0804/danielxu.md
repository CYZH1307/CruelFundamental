# 0804 semophore

semphore 是一种线程/进程间同步的机制, 它可以被抽象为 一堆硬币:

* 当要保证其他线程不会同时访问一块资源时, 需要用 sem.wait() 对它进行保护
* 当 wait 时, 要从这堆硬币里拿走一个
* 当访问完成, 要 post 或者 releases, 此时会加一个硬币
* 当 需要 wait 而没有硬币时, 线程就 block

可以玩下这个 游戏, 加深理解 https://deadlockempire.github.io/#S1-simple