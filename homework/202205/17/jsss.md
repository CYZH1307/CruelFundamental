# C++: STL中deque底层原理是什么？

## deque

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

## 总结

`deque`的实现还是比较精妙的, 很值得学习的. 

- 首先是类的结构的设计,`deque`自身只提供向外的接口, 其调用`deque_base`的具体实现和函数来完成这些接口, 而且通过引入`deque_iterator`, 使得具体数据存储的功能划分更为明确, 而且利用`_M_cur`和`_M_node`可以很高效的完成`deque`中一些函数的实现. 

- 当中央控制器的`node`不够时, 复制现有的`deque`的代价是很低的, 因为我们无需复制底层存储数据的内存, 只需要复制中央控制器以及修改`deque_iterator`中`_M_node`指针的指向即可.