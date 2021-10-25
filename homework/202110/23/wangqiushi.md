Q描述Java线程实现方式
A 3种实现方式
1.继承Thread。编写类，继承Thread ，重写run方法，创建一个新的执行路径，通过thread对象的start( )来启动任务。系统启动线程，执行run（ ）。
2.实现Runnable接口。创建任务对象r，创建一个线程，并为其分配一个任务r，执行线程。
3.实现Callable接口。编写类实现Callable接口 , 实现call方法。编写Callable类对象，创建FutureTask对象，通过Thread,启动线程。在对象上调用get方法就可以获取到Callable任务返回值。
