# Queue


ArrayBlockingQueue, LinkedBlockingQueue 和 ConcurrentLinkedQueue 是比较常用的并发 queue:

* ConcurrentLinkedQueue, LinkedBlockingQueue 都是 先进先出的 queue 结构, 底层使用 linked node 结构
* LinkedBlockingQueue 可以限定/不设定长度限制
* 当 LinkedBlockingQueue 满时, 添加会被block; 当空时, 访问 thread 会被block;
* ConcurrentLinkedQueue 空时, 不会block, 而是返回null
* ArrayBlockingQueue 是必须限定长度的先进先出并发queue, 底层是 array 结构, 在创建时, 提前分配内存
* 和 LinkedBlockingQueue 类似:  queue 满时, 添加会被block; 当空时, 访问 thread 会被block;

总结: 

无锁队列选 ConcurrentLinkedQueue, 有锁队列选不限长度选 LinkedBlockingQueue, 限制长度选 ArrayBlockingQueue (注意内存的影响, 也可选定长 LinkedBlockingQueue)