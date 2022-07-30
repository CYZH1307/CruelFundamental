[//]: fcn.mark
## 并发和并行的区别是什么？

并发(Concurrent) ：多个队伍使用同一个咖啡机，然后队伍间可以根据规则轮换着使用，最终每个人都能接到咖啡

并行(Parallel) 是每个队伍都拥有一个咖啡机，效率更高，因为同时可以有N个人在接咖啡

引用Erlang的作者*Joe Armstrong*的神图：
![Concurrent and Parallel Programming](https://joearms.github.io/images/con_and_par.jpg)

## 为什么并发会比串行慢？

创建线程存在较大开销，且线程上下文切换时也会带来开销。当这些开销慢于串行速度时并发会比串行慢。