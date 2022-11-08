### OOM 时 Linux 会做些什么？

1、OOM killer机制

OOM killer是out-of-memory killer的缩写，它是机Liux内核的一种内存管理机制。在Linux系统内存将要用完的情况下，OOM-killer进程会遍历当前机器上的所有进程，按照进程所占内存的大小和用户打分(oom_score_adj)对进程进行打分（占用内存越大，分数越高），然后挑选出分数最高的进程将其kill掉。

2、寻找系统中最先被OOM kill的进程

一个进程的OOM-killer打分分别由系统得分和用户打分综合评判，它的具体策略是系统打分+用户打分为当前进程的最后分数。对于Linux中运行的每一个进程，都会有两个文件分别为/proc/{pid}/oom_score和/proc/{pid}/oom_score_adj来保存系统打分和用户打分。

oom_score：系统打分是根据当前进程当前时间占用内存计算，占用内存越多，分数越高。
-oom_score_adj:用户打分为用户写入，范围是-1000~1000。若进制OOM kill掉当前进程，可以将该进程的oom_score_adj设置为-1000。

3、关闭OOM killer机制&避免进程被OOM kill
当我们机器上运行着重要的程序时，比如mysql或者redis等，这些程序都是比较吃内存的，所以我们不希望这些进程被系统kill掉，我们可以通过两种方式来达到目的。

- 手从修改进程oom_score_adj的值为-1000避免该进程被kill。
- 禁止的OOM kill机制，当系统发生 oom 的时候打开 kernel panic，此时系统会发生重启。

ref:https://blog.csdn.net/u014630623/article/details/88939100