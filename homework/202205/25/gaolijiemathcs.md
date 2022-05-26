## Java: HashMap HashTable和ConcurrentHashMap的区别

### HashMap和HashTable的区别

- 键值是否可以为null：HashTable不允许键或值为null，HashMap的键和值都可以为null：因为HashTable在put为空值的时候会抛空指针异常，HashMap做了特殊处理。

- 实现方法不同：HashTable继承了Dictionary类，HashMap继承AbstractMap类。

- 初始化容量不同：HashMap初始容量为16，HashTable初始容量为11，两者默认的负载因子都是0.75

- 扩容机制不同：当现有容量大于 总容量 * 负载因子，HashMap扩容为当前容量 * 2，HashTable为当前容量*2+1

- 迭代器不同：HashMaph中的Iterator迭代器是fail-fast的，而HashTable的Enumerator不是fail-fast的。

  - HashTable使用fail-safe机制，此次读到的数据不一定是最新的数据，使用null值会无法判断对应的key是不存在还是为空。无法使用contain(key)进行判断。

  - HashMap为fail-fast，当迭代的过程有人修改集合内容，会抛出异常
    - 实际上是通过modCount，判断修改次数，迭代器在开始迭代的时候会将这个值保存给迭代器的expectedModCount，每次遍历的时候，迭代器会获取这个值，判断modCount是否和expectedModCount一致，不一致则抛出异常。
    - 集合在遍历期间如果内容发生变化会改变modCount值
    - 迭代器使用hashNext()/next() 遍历返回下一个元素，都会检测modCount变量，是否为ExpectedModCount值，是的话就会返回遍历，否则抛出异常。
    - 这个异常检测抛出条件是检测modCount！=expectedmodCount 这个条件。如果集合发生变化时修改modCount值刚好又设置为了expectedmodCount值，则异常不会抛出。因此不能依赖这个异常抛出作为是否并发操作的依据，只是有机会发现并发修改的一个方式。

- 线程安全性：HashMap线程不安全，HashTable线程安全。HashTable线程安全是在所有的操作的都直接上synchornized锁，效率不高。



### HashMap和ConcurrentHashMap区别

- 数据结构不同：
  - HashMap：
    - 1.7中：通过数组+链表的方式。哈希冲突用头插法。
    - 1.8中：通过数组+链表+红黑树的方式。哈希冲突用尾插法。
  - ConcurrentHashMap：（也是fail-fast)
    - 1.7中：数组+链表。分段锁的机制，Segment数组+与HashEntry的一个table作为结点。并且使用volatile修饰数据value
      - volatile保证了变量的内存可见性、禁止指令重排序、通过保证单次读写的原子性。
      - Segment分段锁技术，继承自ReentrantLock，HashTable会导致put和get都需要同步处理，ConcurrentHashMap支持按照Segment数组数量的线程进行并发。每个线程占用一个Segment，是不会影响其他Segment进行操作。每次操作都是先定位一个Segment再进行put或者get操作。
      - put操作会产生获取锁，如果有竞争，则scanAndLockForPut()自旋获取锁。：1、自旋获取锁 2、重试次数到达MAX_SCAN_RETRIES，改为阻塞获取锁保证获取成功。
      - get操作不需要加锁，hash到具体的Segment以后，通过再一次Hash定位到具体的元素。HashEntry中value值是通过volatile关键词修饰的，保证了内存可见性。
      - 效率还是低，数组加链表的方式，因此查询的时候，还是遍历链表。
    - 1.8中：放弃分段锁，通过CAS+synchronized保证并发安全+引入红黑树。HashMap一样，将HashEntry改为了Node，将值和next用volatile修饰保证可见性，红黑树用于当链表比较长大于8转化为红黑树。
      - put操作：
        - 1、根据key算出hashcode，判断是否初始化。
        - 2、当前key定位的Node，如果为空表示当位置可以写入数据，CAS尝试写入，失败则自旋保证成功。
        - 3、当前hashcode == moved == -1 则需要扩容
        - 4、如果都不满足，则利用synchronized锁写入数据。
        - 5、数量大于TREEIFY_THRESHOLD则转换为红黑树。
      - CAS操作：读取数据不加锁，准备写回的时候，比较原值是否修改，如果没有修改则写回，已经被修改则重新执行读取流程。可能会有ABA问题，可以加一个版本号控制解决，每次判断原值是否和最初读取值相等+版本号比较，判断成功版本号+1 并且写入。
      - synchronized在1.8做了升级，加入了锁升级的过程：无锁==>偏向锁==>CAS轻量级锁==>重量级锁。因此效率提升可以使用。
      - get操作：Hashcode寻址，存在则直接返回。如果有哈希冲突。红黑树按照红黑树方式获取，否则按照原有链表获取。
    - 1.8在1.7做了较多改动，红黑树保证查询效率，并且取消ReentrantLock（分段锁Segment继承自ReentrantLock）改为synchronized（1.8优化加入所升级过程）。

