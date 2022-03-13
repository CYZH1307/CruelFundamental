### C++: Lock-free queue 的实现。lock-free和lock-based的区别

------

C++中的 lock-free queue 一般都是基于cas原子操作实现的，即让判断当前节点是不是队列尾，是就入队的操作为原子，来实现入队操作，取出也同理。

lock-free 和 lock-based其实本质上都是锁的机制，但lock-free降低了锁的粒度，将其限制在单一的数据上，相比来说一般更加高效。
