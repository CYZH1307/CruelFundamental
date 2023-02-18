# Python 的深浅拷贝
- https://juejin.cn/post/6844904021866594311

## 整形数
- == 判断值是否相等
- is 判断对象地址是否相同，即是否为同一个对象
- a = 5
- b = 5
- id(a) == id(b0 # true
- a = 257
- b = 257
- id(a) == id(b) # false
- 为了性能优化，Python 对 [-5, 256] 整形保存在一个缓存数组里面
- 如果创建一个范围内的变量，获得的是浅拷贝，而不会创建一个新的内存空间

## 数组
- a = [1, 2, 3]
- b = list(a)
- a == b # true
- a is b # false
- b 是 a 的深拷贝？
- b = copy.copy(a) # 深
- b = a[0:] # 深
- a = tuple(1, 2, 3)
- b = tuple(a) # 浅
- b[1] = 5 # 非法操作
