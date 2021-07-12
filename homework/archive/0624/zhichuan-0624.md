# Singleton

## typescript

```java
public final class Singleton  {  
    private static Singleton instance = new Singleton();  
  
    private Singleton(){  
    }  
  
    public static Singleton getInstance(){  
       return instance；
    }  
}

public final class Singleton {  
    private static Singleton instance = null;  
  
    private Singleton(){  
    }  
  
    public static Singleton getInstance(){  
        if(instance == null) instance = new Singleton();
        return instance；
    }  
}

public final class ThreadSafeSingleton  {  
    private static ThreadSafeSingleton instance = null;  
  
    private ThreadSafeSingleton(){  
    }  
  
    public static Synchronized ThreadSafeSingleton getSingleInstance(){  
        if(instance == null) instance = new ThreadSafeSingleton();
        return instance；
    }  
}

public final class DoubleCheckedSingleton  {  
    private static DoubleCheckedSingleton instance = null;  
  
    private DoubleCheckedSingleton(){  
    }  
  
    public static DoubleCheckedSingleton getSingleInstance(){  
        if(instance == null) {
              Synchronized(DoubleCheckedSingleton.class){
                     if(null == singObj)
                           singObj = new DoubleCheckedSingleton();
              }
         }
       return singObj；
    }  
}


public class Singleton    {    
    private static class SingletonHolder    
    {    
        public final static Singleton instance = new Singleton();    
    }    
   
    public static Singleton getInstance()    
    {    
        return SingletonHolder.instance;    
    }    

```