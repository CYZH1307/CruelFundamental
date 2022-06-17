### Java: 说说ThreadLocal原理?

每个线程Thread都维护了自己的threadLocals变量，在每个线程创建ThreadLocal的时候，数据存在自己的Thread的threadLocals变量里面，从而隔离。

#### 底层结构

底层维护了一个ThreadLocalMap，数据结构有点像Map，实际上Entry继承了WeakReference，不是链表，而是一个数组。

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

用数组是因为一个线程可以有多个ThreadLocal来存放不同类型的对象，但是都将放到当前的ThreadLocalMap当中。

#### set原理

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

ThreadLocalMap在存的时候，会给每一个ThreadLocal对象一个threadLocalHashCode，插入的过程根据Hash值定位到table中的位置int i = key.threadLocalHashCode & (len-1);

判断当前位置是否为空，如果为空就初始化一个Entry对象：

```
if (k == null) {
    replaceStaleEntry(key, value, i);
    return;
}
```

如果i不为空，如果这个Entry对象key正好是即将设置的key就刷新Entry中的value：

```
if (k == key) {
    e.value = value;
    return;
}
```

如果i不为空，且key不是entry，那么就找下一个空位置直到为空。 e = tab[i = nextIndex(i, len)]

#### get原理

根据ThreadLocal对象的hash值，定位Table位置，判断该位置Entry对象中的key是否和get的key一致，如果不一致就判断下一个位置，set和get冲突严重，效率是很低的。

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



#### ThreadLocal内存泄露问题

key保存在ThreadLocalMap中，key和value正常强引用，但是目前弱引用了。

ThreadLocal在没有外部强引用时，发生GC时会被回收，如果创建ThreadLocal的线程一直持续运行，那么这个Entry对象中的value就有可能一直得不到回收，发生内存泄露。

解决方法：代码的最后都调用remove()，使用的最后将remove将值清空。

