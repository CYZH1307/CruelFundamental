Java 异常处理
1. 异常的父类是Throwable
2. Throwable有两个子类Exception和Error
3. Error会导致程序崩溃, 虽然我们可以catch它，但几乎不这么做。例如，outofmemory error表示程序已经不可能正常运行。
4. Exception 分 compile time 和 runtime 两种，前面一种我们需要显式catch。
5. finally block 优先级非常高，如果finally里有返回值的话，会发生一些奇怪的事，所以，我们默认不能这么做。同时在finally里修改变量是无效的。
6. finally可以中断throw，这点需要注意，如果finally有return的话，throw就不能resume了。
7. finally block 中如有异常，处理逻辑与try block类似
