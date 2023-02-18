# C++ 完美转发
- https://zhuanlan.zhihu.com/p/161039484
- https://www.zhihu.com/question/48367350
- https://theonegis.github.io/cxx/C-%E4%B8%AD%E7%9A%84%E4%B8%87%E8%83%BD%E5%BC%95%E7%94%A8%E5%92%8C%E5%AE%8C%E7%BE%8E%E8%BD%AC%E5%8F%91/

## 完美转发
- 保持原来值的属性不变
- 原来是左值，经过std::forward处理后还是左值
- 右值亦然
- print(T & t) -> left value
- print(T && t) -> right value
- print(v);
- print(std::forward<T>(v));
- print(std::move(v));
- v = 1, left, right, right
- v = x, left, left, right
  
 ## 原理
- T&& forward(typename std::remove_reference<T>::type& param)
- return static_cast<T&&>(param); // left
- T&& forward(typename std::remove_reference<T>::type&& param)
- return static_cast<T&&>(param); // right
  
  
