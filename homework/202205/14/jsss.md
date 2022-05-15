# C++: STL中 map, set, multiset, multimap底层原理是什么？

`map`, `set`, `multimap`和`multiset`都是容器适配器, 其内部包含了一个`红黑树`. 利用红黑树来实现其所需的功能. `红黑树`的结点是`pair<key_, value_>`类型. 具体的, `map`的类型为`pair<const key_, value>`, `set`的类型为`pair<const key_, key_>`. `map`, `set`和`multimap`, `multiset`的不同之处在于后者可以出现相同的`key_`, 这是红黑树自身提供的接口实现的, 他们只不过是对`红黑树`进行了所需功能的封装.