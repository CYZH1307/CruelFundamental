# 优先级反转

定义：涉及共享资源的线程调度故障导致高优先级的任务被低优先级的任务一直抢占CPU，长期处于无法运行的状态。
描述：

    1.假设有任务A、B、C，优先级A>B>C，且A和C使用了同一套共享资源S。
    2.C先启动，给S上了排他锁，A第二个启动，遇到S上排他锁则挂起等待排他锁释放。
    3.B第三个启动，优先级高于C，且未被排他锁阻拦，B抢占了CPU，C挂起，且C仍然锁住S。
    4.B执行完，轮到C执行，C再执行完，释放S上的锁，A最后执行。
可见，优先度最高的A最后执行，若一直都有B加入，或者第3步里面B的执行时间较长，则A可能一直拿不到CPU。1997年NASA火星车便遇到了该类故障，导致频繁重启，NASA通过远程打补丁才解决了这个问题。

解决：

    1.优先级天花板：让占有公共资源的C在运行时提升到全局最高的优先级，则任意任务无法抢占。
    2.优先级继承：当A被C所施加的排他锁阻拦时，A将C提升到自己的优先级，则优先级比A低的B无法抢占。
