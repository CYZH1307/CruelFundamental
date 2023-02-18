### C ++ 简述 mechanism of new

Reference: [cnblog](https://www.cnblogs.com/hazir/p/new_and_delete.html)

- new和malloc的区别：

  - malloc申请完空间之后不会对内存进行必要的初始化，直接返回申请内存的指针。

- 标准库函数：分为 object 和 array

  ```c++
  void *operator new(size_t);     //allocate an object
  void *operator delete(void *);    //free an object
  
  void *operator new[](size_t);     //allocate an array
  void *operator delete[](void *);    //free an array
  ```

- new object 的过程

  - 类A:

    ```c++
    class A {
    public:
    	A(int v): var(v) {
        fopen_s(&file, "test", "r");
      }
      ~A() {
        fclose(file);
      }
    private:
      int var;
      FILE * file;
    }
    ```

  - ```c++
    class A *pA = new A(10);
    ```

    

  - 实际的步骤：

    1） 调用 `operator new` 标准库函数，分配足够大的原始的未类型化的内存

    2）运行该类型的相应的构造函数，在上一步分配的内存上进行初始化对象

    3）返回指向新分配并构造的对象的指针

- new array 的过程：

  - ```c++
    class A *pAa = new A[3];
    ```

  - 实际的步骤：

    1）调用`operator new []` 标准库函数来分配足够大的原始未类型化的内存，注意多出4个字节存放数组大小

    2）在刚分配的内存上运行构造函数对新建的对象进行初始化构造

    3）返回指向新分配并构造好的对象数组的指针

  - delete的时候，**调用析构函数的次数**是从数组对象指针前面的 4 个字节中取出，传入`operator delete[]` 函数的参数不是数组对象的指针 `pAa`，而是`pAa`的值**减4**.



### 什么是Python中的装饰器 Decorators

修改其他函数的功能的函数

场景1: Authorization

```python
from functools import wraps
 
def requires_auth(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        auth = request.authorization
        if not auth or not check_auth(auth.username, auth.password):
            authenticate()
        return f(*args, **kwargs)
    return decorated
```



场景2: Logging

```python
from functools import wraps
 
def logit(func):
    @wraps(func)
    def with_logging(*args, **kwargs):
        print(func.__name__ + " was called")
        return func(*args, **kwargs)
    return with_logging
 
@logit
def addition_func(x):
   """Do some math."""
   return x + x
 
 
result = addition_func(4)
# Output: addition_func was called

```



#### 带参数的装饰器

```python
from functools import wraps
 
def logit(logfile='out.log'):
    def logging_decorator(func):
        @wraps(func)
        def wrapped_function(*args, **kwargs):
            log_string = func.__name__ + " was called"
            print(log_string)
            # 打开logfile，并写入内容
            with open(logfile, 'a') as opened_file:
                # 现在将日志打到指定的logfile
                opened_file.write(log_string + '\n')
            return func(*args, **kwargs)
        return wrapped_function
    return logging_decorator
 
@logit()
def myfunc1():
    pass
 
myfunc1()
# Output: myfunc1 was called
# 现在一个叫做 out.log 的文件出现了，里面的内容就是上面的字符串
 
@logit(logfile='func2.log')
def myfunc2():
    pass
 
myfunc2()
# Output: myfunc2 was called
# 现在一个叫做 func2.log 的文件出现了，里面的内容就是上面的字符串
```



##### 装饰器类

- 类也可以用来构建装饰器

```python
from functools import wraps
 
class logit(object):
    def __init__(self, logfile='out.log'):
        self.logfile = logfile
 
    def __call__(self, func):
        @wraps(func)
        def wrapped_function(*args, **kwargs):
            log_string = func.__name__ + " was called"
            print(log_string)
            # 打开logfile并写入
            with open(self.logfile, 'a') as opened_file:
                # 现在将日志打到指定的文件
                opened_file.write(log_string + '\n')
            # 现在，发送一个通知
            self.notify()
            return func(*args, **kwargs)
        return wrapped_function
 
    def notify(self):
        # logit只打日志，不做别的
        pass
      
class email_logit(logit):
    '''
    一个logit的实现版本，可以在函数调用时发送email给管理员
    '''
    def __init__(self, email='admin@myproject.com', *args, **kwargs):
        self.email = email
        super(email_logit, self).__init__(*args, **kwargs)
 
    def notify(self):
        # 发送一封email到self.email
        # 这里就不做实现了
        pass
```



@email_logit 将会和 @logit 产生同样的效果，但是在打日志的基础上，还会多发送一封邮件给管理员。





Reference: [Runood](https://www.runoob.com/w3cnote/python-func-decorators.html#:~:text=%E8%A3%85%E9%A5%B0%E5%99%A8%E6%9C%AC%E8%B4%A8%E4%B8%8A%E6%98%AF,%E9%97%AE%E9%A2%98%E7%9A%84%E7%BB%9D%E4%BD%B3%E8%AE%BE%E8%AE%A1%E3%80%82)


