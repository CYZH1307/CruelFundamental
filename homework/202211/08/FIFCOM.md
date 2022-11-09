## OOM Score 是如何计算的？会受哪些因素的影响？

OOM Score = 内核计算出的 oom_score + 用户指定的 oom_score_adj

值越高，越容易被 Kill

oom_score_adj 范围在 -1000 ~ 1000

特别的，-1000 代表不会被 kill

oom_score = 内存消耗 / 总内存

拥有CAP_SYS_ADMIN特权的进程其内存消耗会被减少 3% 计算

