 ### 什么是CAP原理，什么是FLP不可能定理？
 
 * CAP指的是在一个分布式系统中：Consistency（一致性）、 Availability（可用性）、Partition tolerance（分区容错性），最多只能同时三个特性中的两个，三者不可兼得，最多满足其中的两个特性。
 * FLP 不可能原理：在网络可靠、但允许节点失效（即便只有一个）的最小化异步模型系统中，不存在一个可以解决一致性问题的确定性共识算法（No completely asynchronous consensus protocol can tolerate even a single unannounced process death）。
