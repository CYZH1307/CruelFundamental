### Python: LEGB rule

命名空间法则：LEGB

命名空间是一个字典，{name:object}

- 如需打印全局变量与局部变量的字典映射，可以使用函数`globals()` 和 `locals()`



命名空间查找顺序：local -> enclosing function locals -> global -> builtin



Local:

- 作用范围：当前整个函数体

- 生命周期：当函数被调用时创建一个局部命名空间，当函数返回结果或抛出异常时，被删除。每一个递归调用的函数都有自己的命名空间。

- 如果使用global关键字，也可以修改全局变量

- ```python
  a_var = 'global value'
  
  def a_func():
      global a_var
      a_var = 'local value'
      print(a_var, '[ a_var inside a_func() ]')
  
  print(a_var, '[ a_var outside a_func() ]')
  a_func()
  print(a_var, '[ a_var outside a_func() ]')
  """
  global value [ a_var outside a_func() ]
  local value [ a_var inside a_func() ]
  local value [ a_var outside a_func() ]
  """
  ```

- 如果我们没有明确地告诉Python我们要使用的是全局作用域，而是直接尝试修改变量值的话，就很容易产生UnboundLocalError.

  ```python
  a_var = 1
  
  def a_func():
      a_var = a_var + 1 # UnboundLocalError
      print(a_var, '[ a_var inside a_func() ]')
  
  print(a_var, '[ a_var outside a_func() ]')
  a_func()
  ```

  

Enclosing function locals 外部嵌套函数的命名空间

- 作用范围：一个函数包裹在另一个函数内部
- 我们可以在内部函数中使用`nonlocal`关键字来明确地访问外部(外围函数)

```python
a_var = 'global value'

def outer():
       a_var = 'local value'
       print('outer before:', a_var)
       def inner():
           nonlocal a_var
           a_var = 'inner value'
           print('in inner():', a_var)
       inner()
       print("outer after:", a_var)
outer()
"""
outer before: local value
in inner(): inner value
outer after: inner value
"""
```



Global 全局命名空间

- 作用范围：当前模块（文件）
- 生命周期：在模块定义被读入时创建，通常模块命名空间也会一直保存到解释器退出



Builtin 内建模块命名空间

- 作用范围：所有模块（文件）
- 生命周期：Python解释器启动时创建，会一直保留，不被删除
- 常见内建的函数、类：dict, list, type, print 等等

---

#### For 循环泄漏到全局命名空间 🌟

```python
b = 1
for b in range(5):
    if b == 4:
        print(b, '-> b in for-loop')
print(b, '-> b in global')

# 4 -> b in for-loop
# 4 -> b in global

# Python 3.x 中:
i = 1
print([i for i in range(5)])
print(i, '-> i in global')
# [0, 1, 2, 3, 4]
# 1 -> i in global

```





Reference: [blog](https://www.cnblogs.com/GuoYaxiang/p/6405814.html)

