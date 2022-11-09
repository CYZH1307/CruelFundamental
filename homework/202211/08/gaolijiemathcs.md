### OOM Score 是如何计算的？会受哪些因素的影响？

在发生OOM，OOM Killer选择最坏的进程来进行kill时，如何选择最坏的进程呢？内核采用给进程打分的方法来选择最坏的进程，也就是打分越高，该进程就越坏，就越会选择该进程进行kill。内核通过如下的函数来进行打分：

```c
unsigned long oom_badness(struct task_struct *p, struct mem_cgroup *memcg,
              const nodemask_t *nodemask, unsigned long totalpages)
{……

    adj = (long)p->signal->oom_score_adj;
    if (adj == OOM_SCORE_ADJ_MIN) {－－－－－－－－－－－－－－－－－－－－－－（1）
        task_unlock(p);
        return 0;－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－（2）
    }

    points = get_mm_rss(p->mm) + get_mm_counter(p->mm, MM_SWAPENTS) +
        atomic_long_read(&p->mm->nr_ptes) + mm_nr_pmds(p->mm);－－－－－－－－－（3）
    task_unlock(p);


    if (has_capability_noaudit(p, CAP_SYS_ADMIN))－－－－－－－－－－－－－－－－－（4）
        points -= (points * 3) / 100;

    adj *= totalpages / 1000;－－－－－－－－－－－－－－－－－－－－－－－－－－－－（5）
    points += adj; 

    return points > 0 ? points : 1;
}
```

（1）在对进程进行打分时，需要考虑两方面。一是该进程使用的内存使用情况；二是进程设置的oom_score_adj参数，如果将该参数的值设置为OOM_SCORE_ADJ_MIN（-1000），也就相当于禁止了kill该进程。

（2）这里返回0表示告诉OOM killer该进程是一个good进程。而且在打分时，最低分为1分，我们返回0表示为good process，不会被kill。

（3）系统打分需要看物理内存的消耗情况，也就是RSS、swap file/device的占用部分以及页表的占用的内存情况。

（4）root进程有3%的内存使用特权，因此这里要减去那些内存使用量。

（5）用户可以调整oom_score，通过设置oom_score_adj的值来调整最终的分数points。

如果oom_score_adj为分数，则使points分数降低；反之oom_score_adj为正数时，即惩罚该进程，分数points会增加，被认为时bad process的几率即更大。在实际操作中，需要根据本次内存分配时候可分配内存来计算（如果没有内存分配约束，那么就是系统中的所有可用内存，如果系统支持cpuset，那么这里的可分配内存就是该cpuset的实际额度值）。oom_badness函数有一个传入参数totalpages，该参数就是当时的可分配的内存上限值。实际的分数值（points）要根据oom_score_adj进行调整，例如如果oom_score_adj设定-500，那么表示实际分数要打五折（基数是totalpages），也就是说该任务实际使用的内存要减去可分配的内存上限值的一半。