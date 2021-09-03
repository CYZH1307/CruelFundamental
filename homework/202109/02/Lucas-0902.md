# 实现两线程交替打印
```Java
// 1115 - synchronized
// Rank:98.59%
class FooBar {
    
    private int n;
    private int turn = 0;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            synchronized(this) {
                if (turn == 1) {
                    this.wait();
                }
            	printFoo.run();
                turn = 1 - turn;
                this.notify();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            synchronized(this) {
                if (turn == 0) {
                    this.wait();
                }
            	printBar.run();
                turn = 1 - turn;
                this.notify();
            }            
        }
    }
}
```
