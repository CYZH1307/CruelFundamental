### 什么是信号量？它和锁有什么异同？ 信号量能小于0吗？

#### 什么是信号量？信号量能小于0吗？

- 信号量是通过执行wait(S)/signal(S) 两种原语操作(P/V操作)，解决同步互斥问题的机制。原语是完成某种功能并且无法被分割和中断的操作序列，通常由硬件实现。信号量可以小于0。

- 分类：

  - 整形信号量：wait中 信号量 <=0 就会不停重试。没有遵循让权等待，而是忙等。 

    ```
    wait(S) {
    	while(S <= 0);
    	S = S - 1;
    }
    signal(S) {
    	S = S + 1;
    }
    ```

  - 记录型信号量：增加一个记录资源数目的整形变量，并且增加一个进程链表，链接所有等待资源的进程。

    ```
    typedef struct {
    	int value;
    	struct process *L;
    } semaphore;
    
    // wait操作请求资源 当资源<0的时候 block原语进行阻塞，放弃等待进入等待队列。
    void wait(semaphore S) {
    	S.value--;
    	if(S.value < 0) {
    		add this process to S.L;
    		block(S.L);
    	}
    }
    
    // signal释放资源 仍然 资源<=0 则仍然有等待资源 则唤醒。
    void signal(semaphore S) {
    	S.value++;
    	if(S.value <= 0) {
    		remove a process P from S.L;
    		wakeup(P);
    	}
    }
    ```

  

#### 它和锁有什么异同？

侧重点不一样，信号量推荐用法是用在调度线程，进行多线程同步，适合用在生产者消费者模型当中，按照生产消费合理顺序执行。

锁适合用于共享资源，进行对共享资源的保护。