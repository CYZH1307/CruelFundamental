### C++: shared_ptr是如何实现的？

Reference: https://stackoverflow.com/questions/9200664/how-is-the-stdtr1shared-ptr-implemented

`shared_ptr` 管理一个reference counter 和 一个deleter。

它包含：

1. `T*` 
2. `aux*`: `aux` 是 inner abstract class 包含
   - a counter
   - somthing to make increment / decrement atomic 锁之类的
   - An abstract `virtual destory()=0`
   - a virtual destructor



`aux*` class is derived by a family of templatized classes (parametrized on the type given by the explicit constructor, say `U` derived from `T`), that add:

- a pointer to the object 
- a copy of the `deletor` object given as deletion policy to the explicit constructor (or the default `deletor` just doing delete `p`, where `p` is the `U*` above)
- the override of the destroy method, calling the deleter functor.

```c++
template<class T>
class shared_ptr
{
    struct aux
    {
        unsigned count;

        aux() :count(1) {}
        virtual void destroy()=0;
        virtual ~aux() {} //must be polymorphic
    };

    template<class U, class Deleter>
    struct auximpl: public aux
    {
        U* p;
        Deleter d;

        auximpl(U* pu, Deleter x) :p(pu), d(x) {}
        virtual void destroy() { d(p); } 
    };

    template<class U>
    struct default_deleter
    {
        void operator()(U* p) const { delete p; }
    };

    aux* pa;
    T* pt;

    void inc() { if(pa) interlocked_inc(pa->count); }

    void dec() 
    { 
        if(pa && !interlocked_dec(pa->count)) 
        {  pa->destroy(); delete pa; }
    }

public:

    shared_ptr() :pa(), pt() {}

    template<class U, class Deleter>
    shared_ptr(U* pu, Deleter d) :pa(new auximpl<U,Deleter>(pu,d)), pt(pu) {}

    template<class U>
    explicit shared_ptr(U* pu) :pa(new auximpl<U,default_deleter<U> >(pu,default_deleter<U>())), pt(pu) {}

    shared_ptr(const shared_ptr& s) :pa(s.pa), pt(s.pt) { inc(); }

    template<class U>
    shared_ptr(const shared_ptr<U>& s) :pa(s.pa), pt(s.pt) { inc(); }

    ~shared_ptr() { dec(); }

    shared_ptr& operator=(const shared_ptr& s)
    {
        if(this!=&s)
        {
            dec();
            pa = s.pa; pt=s.pt;
            inc();
        }        
        return *this;
    }

    T* operator->() const { return pt; }
    T& operator*() const { return *pt; }
};
```

