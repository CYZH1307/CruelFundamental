/**
 * Get rid of the double check in Java. When object is on initializing, the instance could be
 * already created and it would allow the other threads to get instance's property which is not or
 * partially initialized
 */
public class Singleton {
  private Singleton() {
    nums = new int[10];
  }

  private static Singleton instance;
  private int[] nums;

  public int getLastElement(int idx) {
    return nums[idx];
  }

  public void set(int idx, int val) {
    nums[idx] = val;
  }

  public static Singleton getInstance() {
    if (instance == null) {
      synchronized (Singleton.class) {
        if (instance == null) {
          instance = new Singleton();
          // simulate initializing this class would take 5 seconds
          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          instance.set(5, 10);
        }
      }
    }
    return instance;
  }

  public static void main(String[] args) throws InterruptedException {
    Runnable task =
        () -> {
          System.out.println(
              Thread.currentThread().getName() + ":" + Singleton.getInstance().getLastElement(5));
        };
    Thread thread1 = new Thread(task, "Thread 1");
    Thread thread2 = new Thread(task, "Thread 2");
    thread1.start();
    // make sure when thread2 is calling `if (instance == null)`, instancce is already created by
    // thread1
    Thread.sleep(1000);
    thread2.start();
  }
}
