Spinlock: a lock which uses busy waiting


The thread that gets the lock is always active, but it doesn't perform any effective task. Using the lock will cause busy waiting.


Usecase: 
avoiding deadlock
when there's very small functionality between lock and unlock

disadvantage:
Do-noting loop: wasting cpu time

Code example:
import java.util.concurrent.locks.ReentrantLock;

public class SpinLock extends ReentrantLock{
    public SpinLock() {
        super();
    }
    
    public void lock() {
        while (!super.tryLock()) {
            // Do nothing
        }
    }
    
    public void unlock() {
        super.unlock();
    }
}
