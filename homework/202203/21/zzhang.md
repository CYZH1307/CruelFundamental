C++中struct和class区别，C语言struct和C++struct区别

- 使用 class 时，类中的成员默认都是 private 属性的；而使用 struct 时，结构体中的成员默认都是 public 属性的。
- class 继承默认是 private 继承，而 struct 继承默认是 public 继承
- class 可以使用模板，而 struct 不能

- C语言的struct 只能包含成员变量，不能包含成员函数，C++中的struct都可以。

  

Python: `os.path` 和 `sys.path` 的区别

- `os.path` 主要是 用于对系统路径文件的操作 
  - `os.path.join()` 用于拼接文件的路径
- `sys.path` 主要是 对 Python 解释器的系统环境参数的操作（动态的改变 Python 解释器搜索路径）

```python
this_dir = os.path.dirname(__file__)
sys.path.insert(0, this_dir + '/..')
```



