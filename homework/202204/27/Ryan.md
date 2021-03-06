# 分布式事务如何解决？TCC了解？

相比普通事务，分布式事务在表面上有两点较大的区别：1.执行的链路化；2.存储的类型和位置的多样化。  
这种复杂化就导致了，普通事务需要遵循的ACID原则即使也适用于分布式场景，但分布式事务若要严格保持ACID，则需要付出更多的性能代价。于是分布式事务有自己的原则CAP。

## 分布式事务的CAP

依次是C-（各节点的）一致性、A-可用性、P-分区容错。这里的C是指不同节点之间的一致性，和ACID里面的单点在事务执行前后的一致性不同。  
与必须满足的ACID不同，CAP是一个不可能三角，不可能有一个系统同时完全满足CAP。对于分布式系统而言，P是基本要求，所以应用通常在C和A中间做出取舍。一般来说，涉及金钱交易的系统都应保证C的实现；涉及实时高并发服务的系统应优先保证A的实现。

## TCC

TCC 其实就是采用的补偿机制，其核心思想是：针对每个操作，都要注册一个与其对应的确认和补偿（撤销）操作。它分为三个阶段：

    Try 阶段主要是对业务系统做检测及资源预留
    Confirm 阶段主要是对业务系统做确认提交，Try阶段执行成功并开始执行 Confirm阶段时，默认 Confirm阶段是不会出错的。即：只要Try成功，Confirm一定成功。
    Cancel 阶段主要是在业务执行错误，需要回滚的状态下执行的业务取消，预留资源释放。
