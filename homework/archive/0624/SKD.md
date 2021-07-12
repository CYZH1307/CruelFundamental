Java:

public class Singleton {
   private static Singleton mySingleton;
   
   private Singleton() {}
   
   public  synchronized static Singleton getInstance() {
        if (mySingleton == null) {
            mySingleton = new Singleton();
        }
        return mySingleton;
   }
}

advantange: you can have a created singleton instance whenever you want to do so, if you never use singleton instance, then you never need to create one, thus you won't waste any 
memory space

disadvantage: performance is bad when you have multiple threads calling getInstance() at the same time. More importantly, when you create a new singleton instance, you do not need 
do synchronization any more.
