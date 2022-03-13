###  Java: TreeMap和TreeSet在排序时如何比较元素？Collections工具类中的sort()方法如何比较元素？

- TreeMap存放键值对,TreeSet存放元素。（底层TreeSet 是了用了TreeMap，相当于用Map中key的比较方法来比较，底层都是红黑树）

  ```
  private transient NavigableMap<E,Object> m;
  TreeSet(NavigableMap<E,Object> m) {
          this.m = m;
  }
  public TreeSet() {
       this(new TreeMap<E,Object>());
  }
  // set的add方法 是往一个TreeMap放
  public boolean add(E e) {
      return m.put(e, PRESENT)==null;
  }
  ```

  - 内部比较器Comparable，对于TreeMap存放的键和TreeSet的元素，所属的类必须实现Comparable接口，接口提供compareTo()方法，在比较的时候TreeMap的比较器Comparable会回调需要比较元素的compareTo()方法从而对比元素。
  - 外部比较器Comparator：比较元素可以不实现Comparable接口，必须构造方法加入Comparator比较器，实现大小比较。

  TreeMap

  ```java
  // split comparator and comparable paths
          Comparator<? super K> cpr = comparator;
          if (cpr != null) {	// comparator 外部比较器
              do {
                  parent = t;
                  cmp = cpr.compare(key, t.key);
                  if (cmp < 0)
                      t = t.left;
                  else if (cmp > 0)
                      t = t.right;
                  else
                      return t.setValue(value);
              } while (t != null);
          }
          else {	// 内部比较器
              if (key == null)
                  throw new NullPointerException();
              @SuppressWarnings("unchecked")
                  Comparable<? super K> k = (Comparable<? super K>) key;
              do {
                  parent = t;
                  cmp = k.compareTo(t.key);
                  if (cmp < 0)
                      t = t.left;
                  else if (cmp > 0)
                      t = t.right;
                  else
                      return t.setValue(value);
              } while (t != null);
          }
  ```

      final int compare(Object k1, Object k2) {
          return comparator==null ? ((Comparable<? super K>)k1).compareTo((K)k2)
              : comparator.compare((K)k1, (K)k2);
      }



- Collection工具类比较元素，有两种重载方法，一种要求存放的对象必须实现Comparable接口实现元素的比较。另外一种，不强制要求容器中的元素可比较，但是需要传入Comparator接口子类型(重写compare方法)，对接口在这里放入比较元素大小。后面按照Arrays.sort的方法去比较，按照有没有传入Comparator方法分开。

  ```
      public static <T> void sort(T[] a, Comparator<? super T> c) {
          if (c == null) {
              sort(a);
          } else {
              if (LegacyMergeSort.userRequested)
                  legacyMergeSort(a, c);
              else
                  TimSort.sort(a, 0, a.length, c, null, 0, 0);
          }
      }
  ```

  

