### LRU 和 LFU 的实现，要非常详细的数据结果和过程结构，尽可能的支持高并发

#### 支持高并发的LRU算法

##### LRU(非多线程安全版本)

LRU算法使用HashMap+链表来实现。

```java
class LRUCache {
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode next;
        DLinkedNode prev;

        public DLinkedNode(int _key, int _value) {
            this.key = _key;
            this.value = _value;
        }
    }

    Map<Integer, DLinkedNode> mp;
    int capacity;
    DLinkedNode head, tail;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        mp = new HashMap<>();
        head = new DLinkedNode(-1, -1);
        tail = new DLinkedNode(-1, -1);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if(!mp.containsKey(key)) {
            return -1;
        }
        DLinkedNode node = mp.get(key);
        removeNode(node);
        moveToHead(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if(mp.containsKey(key)) {
            DLinkedNode node = mp.get(key);
            node.value = value;
            removeNode(node);
            moveToHead(node);
        } else {
            DLinkedNode node = new DLinkedNode(key, value);
            mp.put(key, node);
            moveToHead(node);
            if(mp.size() > capacity) {
                DLinkedNode tailNode =  removeTail();
                mp.remove(tailNode.key);
            }
        }
    }

    public void removeNode(DLinkedNode node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    public void moveToHead(DLinkedNode node) {
        node.next = head.next;
        node.prev = head;
        head.next = node;
        node.next.prev = node;
    }

    public DLinkedNode removeTail() {
        DLinkedNode node = tail.prev;
        removeNode(node);
        return node;
    }
}
```

##### LRU多线程安全版本

为了支持高并发下的线程安全，使用线程安全的ConcurrentHashMap以及线程安全的链表队列ConcurrentLinkedQueue，再加上ReadWriteLock锁机制，保证读写的线程安全。

```java
class LRUCache {
    class DLinkedNode {
        int key;
        int value;

        public DLinkedNode(int _key, int _value) {
            this.key = _key;
            this.value = _value;
        }
    }

    ConcurrentHashMap<Integer, DLinkedNode> mp;
    ConcurrentLinkedQueue<DLinkedNode> queue;
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    Lock writeLock = readWriteLock.writeLock();
    Lock readLock = readWriteLock.readLock();
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        mp = new ConcurrentHashMap<>();
        queue = new ConcurrentLinkedQueue<>();
    }
    
    public int get(int key) {
        readLock.lock();
        try {
            if(!mp.containsKey(key)) {
                return -1;
            }
            DLinkedNode node = mp.get(key);
            moveToTail(node);
            return node.value;
        } finally {
            readLock.unlock();
        }
    }
    
    public void put(int key, int value) {
        // 写锁
        writeLock.lock();
        try {
            if(mp.containsKey(key)) {
                DLinkedNode node = mp.get(key);
                node.value = value;
                mp.put(key, node);
                moveToTail(node);
            } else {
                DLinkedNode node = new DLinkedNode(key, value);
                mp.put(key, node);
                queue.add(node);
                if(mp.size() > capacity) {
                    DLinkedNode removedNode = removeHead();
                    mp.remove(removedNode.key);
                }
            }    
        } finally {
            writeLock.unlock();
        }
        
    }

    // 最新的放在队尾
    public void moveToTail(DLinkedNode node) {
        queue.remove(node);
        queue.add(node);
    }

    public DLinkedNode removeHead() {
        DLinkedNode node = queue.poll();
        return node;
    }
}
```



#### 支持高并发的LFU算法

##### LFU(非多线程安全版本)

LFU算法是淘汰访问频次最低的数据，如果访问频次最低的数据有很多条，需要淘汰最旧的数据。

1、get(key)的时候，返回key对应的value

2、调用get/put方法访问key，key的访问频率+1

3、容量满进行插入，需要将freq频率最低的key删掉，最小的freq有多个，则删除最旧的一个

##### LFU(线程安全版本)

需要使用线程安全的容器。并且在写入的时候，加锁，保证线程的安全。