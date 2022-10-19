### JAVA： 说说ThreadLocal原理

#### 线程隔离原理

set源码

```java
public void set(T value) {
    Thread t = Thread.currentThread();// 获取当前线程
    ThreadLocalMap map = getMap(t);// 获取ThreadLocalMap对象
    if (map != null) // 校验对象是否为空
        map.set(this, value); // 不为空set
    else
        createMap(t, value); // 为空创建一个map对象
}
```

ThreadLocalMap是当前线程Thread一个叫threadLocals的变量中获取的。

```java
ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }
```

```java
public class Thread implements Runnable {
      ……

    /* ThreadLocal values pertaining to this thread. This map is maintained
     * by the ThreadLocal class. */
    ThreadLocal.ThreadLocalMap threadLocals = null;

    /*
     * InheritableThreadLocal values pertaining to this thread. This map is
     * maintained by the InheritableThreadLocal class.
     */
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
  
     ……
```

每个线程Thread都维护了自己的threadLocals变量，所以在每个线程创建ThreadLocal的时候，实际上数据是存在自己线程Thread的threadLocals变量里面的，别人没办法拿到，从而实现了隔离。

#### 底层结构

既然有个Map那他的数据结构其实是很像HashMap的，但是看源码可以发现，它并未实现Map接口，而且他的Entry是继承WeakReference（弱引用）的，也没有看到HashMap中的next，所以不存在链表了。类似一个数组。

```java
static class ThreadLocalMap {

        static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }
        ……
    }    
```

是用数组的方式实现的：

开发过程中可以一个线程可以有多个TreadLocal来存放不同类型的对象的，但是他们都将放到你当前线程的ThreadLocalMap里，所以肯定要数组来存。

Hash冲突解决方法：

```java
private void set(ThreadLocal<?> key, Object value) {
           Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len-1);
            for (Entry e = tab[i];
                 e != null;
                 e = tab[i = nextIndex(i, len)]) {
                ThreadLocal<?> k = e.get();

                if (k == key) {
                    e.value = value;
                    return;
                }
                if (k == null) {
                    replaceStaleEntry(key, value, i);
                    return;
                }
            }
            tab[i] = new Entry(key, value);
            int sz = ++size;
            if (!cleanSomeSlots(i, sz) && sz >= threshold)
                rehash();
        }
```

我从源码里面看到ThreadLocalMap在存储的时候会给每一个ThreadLocal对象一个threadLocalHashCode，在插入过程中，根据ThreadLocal对象的hash值，定位到table中的位置i，**int i = key.threadLocalHashCode & (len-1)**。

然后会判断一下：如果当前位置是空的，就初始化一个Entry对象放在位置i上；

```
if (k == null) {
    replaceStaleEntry(key, value, i);
    return;
}
```

如果位置i不为空，如果这个Entry对象的key正好是即将设置的key，那么就刷新Entry中的value；

```
if (k == key) {
    e.value = value;
    return;
}
```

如果位置i的不为空，而且key不等于entry，那就找下一个空位置，直到为空为止。

在get的时候，也会根据ThreadLocal对象的hash值，定位到table中的位置，然后判断该位置Entry对象中的key是否和get的key一致，如果不一致，就判断下一个位置，set和get如果冲突严重的话，效率还是很低的。

```java
private Entry getEntry(ThreadLocal<?> key) {
            int i = key.threadLocalHashCode & (table.length - 1);
            Entry e = table[i];
            if (e != null && e.get() == key)
                return e;
            else
                return getEntryAfterMiss(key, i, e);
        }

 private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
            Entry[] tab = table;
            int len = tab.length;
// get的时候一样是根据ThreadLocal获取到table的i值，然后查找数据拿到后会对比key是否相等  if (e != null && e.get() == key)。
            while (e != null) {
                ThreadLocal<?> k = e.get();
              // 相等就直接返回，不相等就继续查找，找到相等位置。
                if (k == key)
                    return e;
                if (k == null)
                    expungeStaleEntry(i);
                else
                    i = nextIndex(i, len);
                e = tab[i];
            }
            return null;
        }
```



ref:https://mp.weixin.qq.com/s/LzkZXPtLW2dqPoz3kh3pBQ