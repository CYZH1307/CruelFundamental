### Java: 为什么我们调用start()方法时会执行run()方法，为什么我们不能直接调用 run() 方法？

多线程编程，调用start()是创建新的线程并处于就绪状态，不用等待新线程run()方法执行完成，CPU会异步调度处于就绪状态的线程，执行相应的run()方法。

可以直接调用run()方法，但系统不会创建新的线程，主线程会直接执行对应线程的run方法，只有执行完run()方法才能执行后面的方法，这样就失去多线程异步的意义了，仍然是主线程执行，无法体现多线程编程的特点。