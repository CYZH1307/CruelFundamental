# C++: STL中 vector、list底层原理是什么？


## vector

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


## list

**双向链表**. `cpp`中`list`的实现为双端队列. `STL`的实现中对于模板和不依赖模板的部分进行了分离, 使得代码结构更加分离, 减少代码重复.

先来看看不依赖模板的类和结构体, 主要是**链表结点中的指针域**和**链表结点头**.

```cpp
/// Common part of a node in the %list.
struct _List_node_base
{
    _List_node_base* _M_next;
    _List_node_base* _M_prev;
    // ...
}

/// The %list node header.
struct _List_node_header : public _List_node_base {
    std::size_t _M_size;
    // ...
private:
    _List_node_base* _M_base() { return this; }
}
```

需要模板的部分依赖于具体实例化的类型, 主要有**ListNode**(存储数据, 继承`_List_node_base`, 即其包含指针域)、**ListIterator**(完成迭代器的功能, *, ->, ++, -- 等操作符的重载)、**ListBase**(完成List内存结点分配, 结点数量维护等基础功能, 向List提供实现类**ListImpl**)和**List**(公有继承**ListBase**, 使用ListBase中的基础函数完成List所需的功能).

### ListNode

```cpp
/// An actual node in the %list.
template<typename _Tp>
struct _List_node : public __detail::_List_node_base
{
#if __cplusplus >= 201103L
    // 数据存储
    __gnu_cxx::__aligned_membuf<_Tp> _M_storage;
    _Tp*       _M_valptr()       { return _M_storage._M_ptr(); }
    _Tp const* _M_valptr() const { return _M_storage._M_ptr(); }
#else
    _Tp _M_data;
    _Tp*       _M_valptr()       { return std::__addressof(_M_data); }
    _Tp const* _M_valptr() const { return std::__addressof(_M_data); }
#endif
};
```

### ListIterator

选取了`_List_iterator`的部分源码.

```cpp
/**
*  @brief A list::iterator.
*
*  All the functions are op overloads.
*/
template<typename _Tp>
struct _List_iterator
{
    typedef _List_iterator<_Tp>		_Self;
    typedef _List_node<_Tp>			_Node;

    typedef ptrdiff_t				difference_type;
    typedef std::bidirectional_iterator_tag	iterator_category;
    typedef _Tp				value_type;
    typedef _Tp*				pointer;
    typedef _Tp&				reference;

    // Must downcast from _List_node_base to _List_node to get to value.
    reference operator*() const _GLIBCXX_NOEXCEPT
    { return *static_cast<_Node*>(_M_node)->_M_valptr(); }

    pointer operator->() const _GLIBCXX_NOEXCEPT
    { return static_cast<_Node*>(_M_node)->_M_valptr(); }

    _Self& operator++() _GLIBCXX_NOEXCEPT
    {
        _M_node = _M_node->_M_next;
        return *this;
    }

    // The only member points to the %list element.
    __detail::_List_node_base* _M_node;
};
```

可见`_List_iterator`使用`_List_node_base`(非模板类)来实现指针域的功能(通过运算符重载).

### ListBase


```cpp
/// See bits/stl_deque.h's _Deque_base for an explanation.
template<typename _Tp, typename _Alloc>
class _List_base {
protected:
    typedef typename __gnu_cxx::__alloc_traits<_Alloc>::template
rebind<_Tp>::other				_Tp_alloc_type;
    typedef __gnu_cxx::__alloc_traits<_Tp_alloc_type>	_Tp_alloc_traits;
    typedef typename _Tp_alloc_traits::template
rebind<_List_node<_Tp> >::other _Node_alloc_type;
    typedef __gnu_cxx::__alloc_traits<_Node_alloc_type> _Node_alloc_traits;


struct _List_impl
: public _Node_alloc_type
{
    __detail::_List_node_header _M_node;
    // ...
};

_List_impl _M_impl;

}

```

可见`_List_base`中的`_List_impl`中是包含`_List_node_header`(非模板类), 即链表的头节点, 里面不仅有`next`、`prev`指针域, 还有链表的结点数目计数.

### List

`List`是环形双向链表. 当为空时: `this->_M_impl._M_node._M_next = this->_M_impl._M_node`, 否则`this->_M_impl._M_node`指向末尾, 其`next`域指向`begin()`.

```cpp
template<typename _Tp, typename _Alloc = std::allocator<_Tp> >
class list : protected _List_base<_Tp, _Alloc> {
    using _Base::_M_impl;
    using _Base::_M_put_node;
    using _Base::_M_get_node;
    using _Base::_M_get_Node_allocator;    

    // _M_node 指向 end 结点, 其next指向 begin
    iterator begin() _GLIBCXX_NOEXCEPT
    { return iterator(this->_M_impl._M_node._M_next); }

    iterator end() _GLIBCXX_NOEXCEPT
    { return iterator(&this->_M_impl._M_node); }
}
```