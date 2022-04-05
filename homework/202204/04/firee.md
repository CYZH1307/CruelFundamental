### LRU 和 LFU 的实现，要非常详细的数据结果和过程结构，尽可能的支持高并发

在leetcode上实现了一下

##### LRU：

```c++
struct Node
{
    using lit= list<Node*>::iterator;
    int key;
    int value;
    lit it;
    Node(){}
    Node(int key,int value):key(key),value(value){}
};
using lit= list<Node*>::iterator;

class LRUCache {
public:
    unordered_map<int,Node> ma;
    list<Node*> l;
    int capMax;
    int cap;
    LRUCache(int capacity) {
        capMax=capacity;
        cap=0;
    }
    
    int get(int key) {
        
        if(ma.find(key)==ma.end())
        {
            return -1;
        }
        lit it = ma[key].it;
        l.erase(it);
        l.push_front(&ma[key]);
        ma[key].it=l.begin();
        return ma[key].value;
    }
    
    void put(int key, int value) {
        if(ma.find(key)!=ma.end())
        {
            get(key);
            ma[key].value=value;
            return ;
        }
        
        if(cap==capMax)
        {
            lit it = l.end();
            it--;
            int key = (*it)->key;
            l.erase(it);
            ma.erase(key);
            cap--;
        }
        ma[key]=Node(key,value);
        l.push_front(&ma[key]);
        ma[key].it=l.begin();
        cap++;
    }
};

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache* obj = new LRUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */
```









##### LFU：

```c++

struct Data
{
    int key;
    int value;
    Data () {}
    Data (int key,int value):key(key),value(value){}
};

struct Node
{
    using sit = set<Node>::iterator;
    shared_ptr<Data> d;
    int cnt;
    int inTime;
    sit it;
    Node(){}
    Node(Data *da,int cnt,int inTime):cnt(cnt),inTime(inTime)
    {
        d.reset(da);
    }
    bool operator < (const Node& b) const {
        if(cnt!=b.cnt)
        return cnt<b.cnt;
        return inTime<b.inTime;
    }
};
using sit = set<Node>::iterator;
class LFUCache {
public:

    int cap;
    int capMax;
    int time;
    map<int,sit> ma;
    set<Node> se;
    LFUCache(int capacity) {
        cap=0;
        capMax=capacity;
        time=0;
    }
    void put(int key,int value,int cnt)
    {
        time++;
        Node a={new Data(key,value) ,cnt,time};
        se.insert(a);
        ma[key] = se.find(a);
    }



    int get(int key) {
        if(capMax==0)   return -1;
        if(ma.find(key)==ma.end())
        {
            return -1;
        }
        sit it = ma[key];
        int cnt=it->cnt;
        int value=it->d->value;
        se.erase(it);
        ma.erase(key);
        put(key,value,cnt+1);
        return value;
    }
    
    void put(int key, int value) {
        if(capMax==0)   return;
        if(ma.find(key)!=ma.end())
        {
            ma[key]->d->value=value;
            get(key);
            return;
        }
        if(cap==capMax)
        {
            sit it = se.begin();
            int eKey=it->d->key;
            se.erase(it);
            ma.erase(eKey);
            cap--;
        }
        put(key,value,1);
        cap++;
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */
```



高并发场景思路：

没有