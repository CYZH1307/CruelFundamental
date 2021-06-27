/**
 * If have knowledge of how Java load a class, the below implement should be your best choice to
 * create a Singleton. It's thread safe, and only when you call getInstance(), the inner class is
 * created and instance get initialized.
 */
public class Singleton {
  private Singleton instance;

  public static Singleton getInstance() {
    return InstanceHolder.instance;
  }

  private static class InstanceHolder {
    private static final Singleton instance = new Singleton();
  }
}
