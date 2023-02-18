# hashmap和treemap的区别，除了红黑树还可以怎么实现treemap

HashMap和TreeMap，对KV键值对进行存储，而TreeMap会依据key进行排序，而HashMap为无序的存储。

## HashMap和TreeMap区别

### HashMap

HashMap为基于哈希散列表，实现AbstractMap接口，根据键的hashcode无序的插入。数据结构由数组和链表组成，将KV以的方式存入数据结构(java7为entry java8为Node)。HashMap的键值可以为null。非安全的Map。

put方法依据对key的hash值进行找到对应的数组的位置，插入对应数组的位置形成链表(Java8之前是头插法，Java8之后为尾插法，原因与扩容机制有关系)；当插入的时候如果数组同一位置链表过长，大于一定长度(默认为8),则会将链表转化为红黑树；当数组容量大于负载因子，则会引发扩容。

get方法如果是数组位置上的唯一一个点，直接返回，有冲突，则通过key.equals(k)方法去比较去找对应的结点(如果重写了equals方法一定要重写hashcode，因为两个属性完全相同的对象，但是地址不一样，没有重写hashcode hash不一样，但是逻辑上是相等，导致put成功，但是get的时候发现获取的是另外的值)。



1、扩容机制(尾插法改为头插法原因)：与扩容机制有关，当数组负载超过了负载因子(默认0.75)，则会引发扩容，重新resize一个两倍的HashMap，并且重新调用hash方法。扩容具体分为两步骤

step1:数组扩容。创建一个新的Entry空数组，长度为原来数组的两倍。

step2:Rehash：遍历原有的Entry数组，将所有Entry数组重新Hash到新的数组。

注：

- 长度扩大以后hash也变化了 index = hashcode(key) & (length - 1). 其中length为新的长度。

- 用原来的尾插法，有可能在rehash之后几个元素重新在同一个位置进行插入，出现环形链表。

2、JDK8中HashMap数组长度初始化为16：原因是为了位运算方便，因为hash()以后 要映射到我们的数组长度，可以直接用数组长度与hashcode进&操作，index = hash(key)&(length-1) 这里 length为2的倍数，-1后低位都是1，方便做与运算，直接取低位。

3、重写equals方法一定要重写hashcode：因为未重写equals方法继承了object equals方法(比较对象内存地址)，new了两个对象内存地址不一样，值对象==比较对象值，对于引用对象，比较的是地址。HashMap通过key的hashcode去找index，index形成链表，具体去get的时候，找到key对应的hashcode找到相同index，后面要通过equals方法进行判断是不是同一个Node。因此如果重写了equals方法，则一定要重写hashcode,保证不同0的对象返回不同的hash值。hashcode到了同一个链表对象被认为都一样。



### TreeMap

TreeMap底层结构为红黑树，实现了NavigableMap接口(上层为Sorted接口)。依赖红黑树保证key有序(默认为升序)，所有操作依赖红黑树。红黑树的插入、查询和删除时间复杂度都是O(logn),但是删除和插入可能会调整树的结构。键值都不能为null，也是非安全的Map。可以得到一个有序的结果集。

TreeMap可以依据提供的Comparator进行排序，重写compareTo函数。要注意如果比较器返回的结果为0表示在TreeMap为同一个结点，会直接进行setValue的替换，因此要做好区分。

红黑树特点：

```
1. 结点是红色或者黑色
2. 根是黑色
3. 所有的叶子结点都是黑色(叶子结点为NIL结点)
4. 每个红色结点必须有两个黑色的自结点.(每个叶子到根上的路径不能有两个连续的红色结点。)
5. 从任一结点到其每个叶子结点的所有简单路径都包含相同数目的黑色结点。(约束了根到叶子结点最长的可能路径不多于最短的可能路径的两倍长，树的大致平衡。因此操作的插入、删除和查找的最坏情况都和树的高度成比例。)
```



### 区别小结

- 查找方式
  - HashMap  数组+链表+红黑树。查找过程通过哈希表hashcode()对kv进行查找，HashMap元素无序。
  - TreeMap 红黑树。通过红黑树进行存储，TreeMap元素有序。
- 都是线程不安全
- 继承类
  - HashMap继承自AbstractMap，覆盖hashcode() equals()方法，确保属性相等的两个kv是同样的哈希值。
  - TreeMap继承SortedMap 确保键值有序。
- 属性
  - HashMap依据hash实现，使用HashMap可以重写equals和hashcode方法，还可以优化初始容量(2的幂次和负载因子)
  - TreeMap直接用红黑树实现。
- 适用场景
  - HashMap适用于Map插入删除定位元素。
  - TreeMap插入删除触发红黑树调整，有性能消耗。适用于对有序键存储遍历需求的场景。



## TreeMap除了红黑树还可以怎么实现

个人感觉，除了红黑树，也可以用二叉查找树来做，但有可能会退化为链表。

或者用一个有序的双向链表存，但是缺少查找树的特点，遍历为O(N)。

B/B+树，不太好维护。

有序的方式将kv存下来就能实现，但是要考虑实际的性能。

