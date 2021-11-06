# 设计模式原理

## Singleton
- one of 24 Gang of Four Design patterns
- one of creaional design patterns
- will restrict a class to instantiate multiple objects
- only one instance per process

## Scenario
- logging
- driver objects
- configurations
- caching
- thread pool
- database connections
- e.g. java.lang.Runtime

## Early Initialization
- early, initialize at the time of class loading
- pros: simple to implement
- cons: waste CPU/memory as the class may not be used
- cons: cannot handle exception

```java
private static final Singleton instance = new Singleton();
private Singleton() {}
public static Singleton getInsatnce() { return instance; }
```

## Lazy Initialization
- lazy, initialize when used
- pros: thread safe
- pros: save resources until used
- pros: beter performance after moving synchronized from method to variable
- Double Checked Locking Pattern
- Bill Pugh Singleton Implementation prior to Java5
```java
private static Singleton instance;
private Singleton() {}
public static Singleton getInstance() {
  if (null == instance) {
    synchronized (Singleton.class) {
      if (null == instance) {
        intance = new Singleton();
      }
    }
  }
}
```

## Implementation with enum
- broken by reflection or deserialization
```java
public enum Singleton {
  INSTANCE;
  Singleton() {}
}
```
