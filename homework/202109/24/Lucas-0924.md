# Raft Figure 8，为什么Leader不能直接commit前一个任期留下了的log？如果这样做会出现什么问题？

群友是这样理解的，如果状态C中的Index2已经提交，那么S5就不会被选为领导。 

既然被选成领导了，数据肯定是最新的。 

新的领导也知道上任期Term2内,Index2被复制的数量。虽然数量大于半数，假设可以提交，Index2提交了上任期的数据。 

但是此时的主节点S5的Index2并没有Term2的数据,它的Index2可能接受Term3的数据。 

此时，集群Index2的数据不一致了。 

===

fzp:
如果可以的话 你看在term c时刻s1 就可以commit index=2 data=2但是在term d时刻  
s5是leader 发送index=2 data=3 那么此时 其他的s1会在log接收这个index=2 data=3 
但是由于你之前 index=2已经commit了data=2 那么就会有log不一致的情况 
所以raft只允许当前term的leader统计当前term log是否过半才可以去commit

fzp:
"群友是这样理解的，如果状态C中的Index2已经提交，那么S5就不会被选为领导。" 这句话有问题，是可以的，在term C中，S5可以赢得S2 S3 S4与自己的选票。raft在试图request vote的时候，会被更新的candidate拒绝投票，但是考虑termC, 此时S5 是[(term 1, data), (term 3, data)], 而S2,S3都是[(term1, data), (term2, data)] S5最后一个log是term3高于S2的term2，所以S5是up to date。
原文 "Raft determines which of two logs is more up-to-date by comparing the index and term of the last entries in the logs. If the logs have last entries with different terms, then the log with the later term is more up-to-date. If the logs end with the same term, then whichever log is longer is more up-to-date"

fzp:
“新的领导也知道上任期Term2内,Index2被复制的数量” 实际上不知道

fzp:
因为如果知道，那么一定存在leader到下一个leader间的通信，而raft并没有此种通信
