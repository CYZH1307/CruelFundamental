用熟悉的实现支持多线程的单例模式，并列举优缺点?
public class Singleton {
private volatile  static Singleton singleton;
private  Singleton(){};
public static Singleton getSingleton(){
if(singleton==null){
synchronized (Singleton.class){
if(singleton==null){
singleton=new Singleton();
}
}
}
return  singleton;
}
}
优点：是判空后用synchronized锁定了代码块，避免多个线程同时获取单例造成创建多个实例
同时在获取到synchronized锁定的Singleton.class对象后再次判断singleton是否已经被初始化，
方式在争抢到锁之前singleton已经被初始化，防止new Singleton对象。
缺点：上锁在并发竞争激烈的时候可能性能不是特别好。