# C++: STL中有哪些容器？分别有什么特点？

## 序列式容器

### vector

**动态数组**. 其模板类中包含了三根指针, 分别指向`begin`、`end`和`end of storage`. vector的大小`size = end - begin`, 其容量`cap = end of storage - begin`.

```cpp
struct _Vector_impl_data {
    pointer _M_start;           // begin
    pointer _M_finish;          // end
    pointer _M_end_of_storage;  // end of storage
}
```

我们来看一个标准库中的使用场景:

```cpp
// [23.2.4.2] capacity
/**  Returns the number of elements in the %vector.  */
size_type
size() const _GLIBCXX_NOEXCEPT
{ return size_type(this->_M_impl._M_finish - this->_M_impl._M_start); }

/**
*  Returns the total number of elements that the %vector can
*  hold before needing to allocate more memory.
*/
size_type
capacity() const _GLIBCXX_NOEXCEPT
{ return size_type(this->_M_impl._M_end_of_storage
        - this->_M_impl._M_start); }
```

很简单直接的计算`vector的size和caoacity`的方式.

由上可见, 在64位机器上, `sizeof(vector<T>)`的大小为24.

### string

**字符串**. `cpp`中`string`为`basic_string<char>`的别名. 即`typedef basic_string<char>    string;`. 因此我们查看``basic_string<char>`是如何实现的即可.

```cpp
    struct _Alloc_hider : allocator_type // TODO check __is_final
    {
        pointer _M_p; // The actual data.
    };

    // basic_string<char>
    _Alloc_hider	_M_dataplus;
    size_type		_M_string_length;

    // sizeof(_CharT) = 1, 因此 _S_local_capacity = 15
    enum { _S_local_capacity = 15 / sizeof(_CharT) };

    /* M_local_buf 为栈上缓存, 当字符串长度超过15时,  _M_allocated_capacity 为有效值, 表示其字符串容量 */
    union {
        _CharT           _M_local_buf[_S_local_capacity + 1];
        size_type        _M_allocated_capacity;
    };
```

我们可以发现`_Alloc_hider`为`string`内存的实现, 其结构体中的`pointer _M_p`指向字符串的首地址. 重要的是, 该模板类中还存在一个**联合体**(同一时刻只有一个值有效).

当**字符串长度 <= 15**时, 其在栈上分配(`local buf`), 当长度超过15时, 其通过 `_M_dataplus._M_p` 指向字符串的其实地址(一般在堆中), 此时 `_M_allocated_capacity` 指示字符串的容量.

下面我们看一个具体应用场景(`string`移动构造函数)来验证我们的想法:

```cpp
/**
*  @brief  Move construct string.
*  @param  __str  Source string.
*
*  The newly-created string contains the exact contents of @a __str.
*  @a __str is a valid, but unspecified string.
**/
basic_string(basic_string&& __str) noexcept
: _M_dataplus(_M_local_data(), std::move(__str._M_get_allocator()))
{
if (__str._M_is_local())
{
    traits_type::copy(_M_local_buf, __str._M_local_buf,
            _S_local_capacity + 1);
}
else
{
    _M_data(__str._M_data());
    _M_capacity(__str._M_allocated_capacity);
}

// Must use _M_length() here not _M_set_length() because
// basic_stringbuf relies on writing into unallocated capacity so
// we mess up the contents if we put a '\0' in the string.
_M_length(__str.length());
__str._M_data(__str._M_local_data());
__str._M_set_length(0);
}
```

如果移动的`string`为`local string`(即长度 <= 15, 内容存储在`union`中), 则执行:

`traits_type::copy(_M_local_buf, __str._M_local_buf, _S_local_capacity + 1);` 

这里使用拷贝的原因是, 我们必须保证当前字符串的生命周期不依赖于被`move`的字符串, 必须使用当前栈上的存储空间来保存字符串; 另一方面, 其长度 <= 15, 我们就应该通过拷贝来将其复制到`uinon`中, 这样处理起来是统一的. 

如果不是`local string`(即长度 > 15, 内容存储在`[_M_dataplus._M_p, _M_dataplus._M_p + _M_string_length`)中, 我们将指向字符串的指针和容量参数进行拷贝即可.

最后我们拷贝字符串的长度(不受`local`还是`非local`的影响),  最后将`被move`的字符串清空. 

最后当我们对`空string`取`sizeof`时, 由上可见, 其大小为 `sizeof(pointer) + sizeof(size_type) + 16` = 8 + 8 + 16 = 32 (64位机器下).

### deque

**双端队列**. 实现较为复杂, 这里给出一个比较好的回答的[链接](https://stackoverflow.com/questions/6292332/what-really-is-a-deque-in-stl), 回答1和2的图示和代码已经能够说明其完整的实现. 

其实现类似于拉链法哈希表的思路, 第一维中央控制器`map`中的每个值指向一个固定长度的一维数组, 在标准库的实现中指向的是`_Deque_iterator`, 再由`_Deque_iterator`指向具体的内存, 并且`_Deque_iterator`中`first`和`last`可以框定内存的范围, `cur`指向当前的位置(该`chunk`可能有些位置还未使用. 如果是`map::start`, 那么该节点的`cur`指向第一个数据, 且该`chunk`中`cur`左边的所有位置是`unused`, 右边的位置全部被使用了; 如果是`map::finsh`, 那么该节点的`cur`指向最后一个数据的下一位置, 且该`chunk`中`cur`左边位置全被使用了, 右边的位置是`unused`). 

`deque`的具体实现在`_Deque_base::_Deque_impl`中, 主要包含了四个变量:

1. `_Map_pointer _M_map`: 指向中央控制器的指针.
2. `size_t _M_map_size`: 中央控制器的大小. 
3. `iterator _M_start`: 指向中央控制器可用`node`中最左边的`node`.
4. `iterator _M_finish`: 指向中央控制器可用`node`中最右边的`node`.

注意到的是, `_Deque_iterator`中也存储了指向中央控制器的指针: `_Map_pointer _M_node`, 通过该指针可以确定当前`node`在中央控制器中的位置, 并且实现`random access`.

为了降低复制(重新构造`deque`)的代价(该代价为申请新的中央控制器并修改`_Deque_iterator`中`_M_node`指针的指向). `_M_start`和`_M_finish`初始化的时候应尽可能靠近中央控制器的中心的, 这样在`push_front`和`push_back`的时候, 两边会有较为均衡的空间去使用.

另外注意到, 在`_Deque_iterator`存在如下别名定义:

`typedef std::random_access_iterator_tag	iterator_category;`

即`deque`支持随机访问, 使用该迭代器traits, 配合偏特化, 可以高效的实现一些函数. 如下所示:

```cpp

deque(initializer_list<value_type> __l,
const allocator_type& __a = allocator_type())
: _Base(__a)
{
_M_range_initialize(__l.begin(), __l.end(),
        random_access_iterator_tag());
}
```

即支持随机访问的迭代器, 那么我们就可以向数组那样去初始化它. 如`for (auto cur = begin(); cur < end(); ++ cur)`这样.

最后分析以下空`deque`的大小. 由上可见, `deque`的大小就是其中央控制器`map`的大小, `map`中由四个变量, `map pointer`和`map size`均为8字节, `start`和`finsh`的大小为四个指针的大小, 即32字节. 因此空`deque`的大小是80.

#### 总结

`deque`的实现还是比较精妙的, 很值得学习的. 

- 首先是类的结构的设计,`deque`自身只提供向外的接口, 其调用`deque_base`的具体实现和函数来完成这些接口, 而且通过引入`deque_iterator`, 使得具体数据存储的功能划分更为明确, 而且利用`_M_cur`和`_M_node`可以很高效的完成`deque`中一些函数的实现. 

- 当中央控制器的`node`不够时, 复制现有的`deque`的代价是很低的, 因为我们无需复制底层存储数据的内存, 只需要复制中央控制器以及修改`deque_iterator`中`_M_node`指针的指向即可.

### stack 和 queue

**栈**和**队列**. 这两个是标准的容器适配器, 其拥有一个`deque`. 它们的功能都由`deque`来实现. 栈和队列只不过是对强大的`deque`的接口进行了必要的封装, 提供其期望的函数.

```cpp
/*
* queue的模板类
*/
template<typename _Tp, typename _Sequence = deque<_Tp> >
///  @c c is the underlying container.
_Sequence c;


/*
* stack的模板类
*/
template<typename _Tp, typename _Sequence = deque<_Tp> >
//  See queue::c for notes on this name.
_Sequence c;
```


## 关联式容器

关联式容器主要分为**基于哈希表的**`unordered_set`和`unordered_set`和**基于红黑树**的`map`和`set`.

哈希表是通过拉链法实现的, 这里给出一个解释比较直观的[文章](https://jbseg.medium.com/c-unordered-map-under-the-hood-9540cec4553a)


下面给出一个`demo`. 使用**自定义哈希**和**自定义比较器**来使用`unordered_map`和`map`.


```cpp
#include <bits/stdc++.h>
#include <cstddef>
#include <functional>
#include <unordered_map>
#include <unordered_set>

using namespace std;

struct Point {
    int x, y;

    Point (int _x, int _y) : x(_x), y(_y) {} 

    /* 按照 x从小到大排 */
    bool operator < (const Point& p) const {
        return x < p.x;
    }

    /* unordered_map 需要判断元素是否相同 */
    bool operator == (const Point& p) const {
        return p.x == x and p.y == y;
    }
};


/* 自定义hash函数 */
struct HashPoint {
    size_t operator () (const Point& p) const {
        auto str = to_string(p.x) + "_" + to_string(p.y);
        hash<string> _hs;
        return _hs(str);
    }
};


int main() {

    unordered_map<Point, int, HashPoint> cnt;

    Point a(1, 2);
    Point b(1, 2);
    Point c(2, 3);
    Point d(2, 3);
    
    ++ cnt[a];
    ++ cnt[b];
    ++ cnt[c];
    ++ cnt[d];

    // size = 2
    cout << "cnt size = " << cnt.size() << "\n";

    for (auto& [k, v] : cnt)
        printf("x = %d, y = %d, cnt = %d\n", k.x, k.y, v);

    cout << "======================\n";

    map<Point, int> mp;

    ++ mp[a];
    ++ mp[b];
    ++ mp[c];
    ++ mp[d];

    for (auto& [k, v] : mp)
        printf("x = %d, y = %d, cnt = %d\n", k.x, k.y, v);

    /*使用*/
    auto comp = [] (const Point& a, const Point& b) {
        return a.y < b.y;
    };

    map<Point, int, decltype(comp)> mpComp(comp);
    
    ++ mpComp[a];
    ++ mpComp[b];
    ++ mpComp[c];
    ++ mpComp[d];

    cout << "======================\n";

    for (auto& [k, v] : mpComp)
        printf("x = %d, y = %d, cnt = %d\n", k.x, k.y, v);

    return 0;
}

/*
cnt size = 2
x = 2, y = 3, cnt = 2
x = 1, y = 2, cnt = 2
======================
x = 1, y = 2, cnt = 2
x = 2, y = 3, cnt = 2
======================
x = 1, y = 2, cnt = 2
x = 2, y = 3, cnt = 2
*/
```

## 实验环境

- Linux 20.04.1-Ubuntu
- g++ (Ubuntu 9.4.0-1ubuntu1~20.04.1) 9.4.0