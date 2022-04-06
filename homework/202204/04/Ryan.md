# LRU 和 LFU 的实现，要非常详细的数据结果和过程结构，尽可能的支持高并发

### LRU（只写最朴素的实现，最优实现参考LFU）

记录最大容量cap、每次操作的时间序号T，并使用三个hashmap，依次为

    map<int, int> mp; // <key, value>
    map<int, int> mp0; // <key, time>
    map<int, int> mp1; // <time, key>
    
每次get(key)的时候，首先T++；然后取得mp0内key对应的time_old，更新mp0(key)至{key,T}；删除mp1(time_old)，插入{T,key}。最后返回mp(key)。  
每次put(key, value)的时候，首先T++；然后分类讨论：

##### ·mp内存在{key, value}

更新mp0与mp1对应的time到T。

##### ·mp内不存在{key, value}，且mp的size小于cap

为mp插入{key, value}，并更新mp0与mp1对应的time到T。

##### ·其他情况（mp内不存在{key, value}，且mp的size等于cap）

此时需要踢出最老的数据，通过mp1找到最老的time_oldest，和对应的key_oldest，删除它；然后删除mp0(key_oldest)。为mp插入{key, value}，并更新mp0与mp1对应的time到T。

### LFU

用一个结构体Node记录所有信息key，value，time，num（该kv被调用的次数）。并重载运算符<使得Node的大小按num从低到高排列，若num相等则按time从低到高排列

```c++

struct Node {
    int key, value, time, num;
    Node(int k, int v, int t, int n_): key(k), value(v), time(t), num(n_) {}
    bool operator < (const Node &rhs) const {
        if (num != rhs.num) {
            return num < rhs.num;
        }
        return time < rhs.time;
    }
};

```

记录最大容量cap、每次操作的时间序号T，并使用一个hashmap来记录每个Node

    unordered_map<int, Node> mp; // <key, Node>

每次get(key)的时候，首先T++；然后取得mp内key对应的Node，更新其time到T，其num++。  
每次put(key, value)的时候，首先T++；然后分类讨论：

##### ·mp内存在{key, mp(key)}

更新该key对应的Node的time到T，其num++。

##### ·mp内不存在{key, mp(key)}，且mp的size小于cap

为mp插入{key, Node(key, value, T, 1)}。

##### ·其他情况（mp内不存在{key, mp(key)}，且mp的size等于cap）

此时需要踢出一条数据，通过Node中重载的<，我们可以在里面mp里面找到key_worst，删除它；然后插入{key, Node(key, value, T, 1)}。


