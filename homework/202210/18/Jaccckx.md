内联函数
在调用函数时不会出现独立的栈，而是将代码直接拷贝至调用处中执行
内联函数可以避免在调用小函数时的压栈恢复等问题因此性能更好，负面影响就是会增大二进制文件体积
C 使用 inline 关键字并非一定遵守，同时不使用也并非一定不内联，具体要看函数的复杂度和优化级别
macro 仅仅是普通的文本替换，实现一些复杂逻辑时比较困难
ThreadLocal
ThreadLocal 是一个保存在线程中的变量，用于避免竞态条件
ThreadLocal 本质是对 Thread 类中的 threadLocals 的变量（类型为 ThreadLocal.ThreadLocalMap）的引用，其真正的存在于线程中的保证是 Thread 实现的
对 ThreadLocal 的访问实质上是访问了所在 thread 的 threadLocals，可以理解为它只是一个获取 ThreadLocalMap + 辅助获取 ThreadLocalMap 中的值的封装
